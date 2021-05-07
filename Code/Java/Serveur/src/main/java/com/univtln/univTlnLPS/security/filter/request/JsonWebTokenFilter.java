package com.univtln.univTlnLPS.security.filter.request;


import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.net.server.LPSServer;
import com.univtln.univTlnLPS.security.MySecurityContext;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.java.Log;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class if a filter for JAX-RS to perform authentication via JWT.
 */
@JWTAuth
@Provider
@Priority(Priorities.AUTHENTICATION)
@Log
public class JsonWebTokenFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    //We inject the data from the acceded resource.
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        //We use reflection on the acceded method to look for security annotations.
        Method method = resourceInfo.getResourceMethod();

        //if its PermitAll access is granted (without specific security context)
        if (method.isAnnotationPresent(PermitAll.class)) return;

        //otherwise if its DenyAll the access is refused
        if (method.isAnnotationPresent(DenyAll.class)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Access denied to all users").build());
            return;
        }

        //We get the authorization header from the request
        final String authorization = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);

        //We check the credentials presence
        if (authorization == null || authorization.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Please provide your credentials").build());
            return;
        }

        //We get the token
        final String compactJwt = authorization.substring(AUTHENTICATION_SCHEME.length()).trim();
        if (!authorization.contains(AUTHENTICATION_SCHEME) || compactJwt.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Please provide correct credentials").build());
            return;
        }

        String email = null;

        //We check the validity of the token
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer("sample-jaxrs")

                    .setSigningKey(LPSServer.KEY)

                    .build()
                    .parseClaimsJws(compactJwt);
            email = jws.getBody().getSubject();

            //If present we extract the allowed roles annotation.
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                EnumSet<Utilisateur.Role> rolesSet =
                        Arrays.stream(rolesAnnotation.value())
                                .map(Utilisateur.Role::valueOf)
                                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Utilisateur.Role.class)));

                //We check if the role is allowed

                Superviseur superviseur = null;
                try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {
                    List<Superviseur> liste = superviseurDAO.findByEmail(email);
                    if (!liste.isEmpty())
                        superviseur = liste.get(0);
                }

                if (!BasicAuthenticationFilter.isUserInRoles(rolesSet, superviseur))
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                            .entity("Roles not allowed").build());

            }

            //We build a new securitycontext to transmit the security data to JAX-RS
            requestContext.setSecurityContext(MySecurityContext.newInstance(AUTHENTICATION_SCHEME, email));
        } catch (JwtException e) {
            requestContext.abortWith(Response.status(498)
                    .entity("Wrong JWT token. " + e.getLocalizedMessage()).build());
        }

    }
}