package com.univtln.univTlnLPS.security.filter.request;



import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.security.MySecurityContext;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
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
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;



/**
 * Authentication filter is a JAX-RS filter (@Provider with implements ContainerRequestFilter) is applied to every request whose method is annotated with @BasicAuth
 * as it is itself annotated with @BasicAuth (a personal annotation).
 * It performs authentication and check permissions against the acceded method with a basic authentication.
 */
@BasicAuth
@Provider
@Priority(Priorities.AUTHENTICATION)
@Log
public class BasicAuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    //We inject the data from the acceded resource.
    @Context
    private ResourceInfo resourceInfo;

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext requestContext) {
        //We use reflection on the acceded method to look for security annotations.
        Method method = resourceInfo.getResourceMethod();
        //if it is PermitAll access is granted
        //otherwise if it is DenyAll the access is refused
        if (!method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Access denied to all users").build());
                return;
            }

            //We get the authorization header
            final String authorization = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);

            //We check the presence of the credentials
            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Please provide your credentials").build());
                return;
            }

            //We extract the username and password encoded in base64
            final String encodedUserPassword = authorization.substring(AUTHENTICATION_SCHEME.length()).trim();

            //We Decode username and password (username:password)
            String[] emailAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes())).split(":");

            final String email = emailAndPassword[0];
            final String password = emailAndPassword[1];

            log.info(email + " tries to log in");

            //We verify user access rights according to roles
            //After Authentication we are doing Authorization
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

                EnumSet<Utilisateur.Role> rolesSet =
                        Arrays.stream(rolesAnnotation.value())
                                .map(Utilisateur.Role::valueOf)
                                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Utilisateur.Role.class)));

                //We check to email/password
                List<Superviseur> liste = null;
                Superviseur superviseur = null;
                try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {
                    liste = superviseurDAO.findByEmail(email);
                }


                if (!liste.isEmpty()) {
                    superviseur = liste.get(0);

                    if (!superviseur.checkPassword(password)) {
                        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                .entity("Wrong username or password").build());
                        return;
                    }
                }

                //We check if the role is allowed
                if (!isUserInRoles(rolesSet, superviseur))
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Roles not allowed").build());

                //We build a new SecurityContext Class to transmit the security data
                // for this login attempt to JAX-RS
                requestContext.setSecurityContext(MySecurityContext.newInstance(AUTHENTICATION_SCHEME, email));

            }
        }
    }

    public static Boolean isUserInRoles(EnumSet<Utilisateur.Role> rolesSet, Superviseur superviseur){
        for (Utilisateur.Role role:
             rolesSet) {
            if (isUserInRole(role.toString(), superviseur))
                return true;
        }
        return false;
    }

    public static Boolean isUserInRole(String role, Superviseur superviseur){
        // Tout le monde a les autorisations utilisateurs (guest)
        if (role.equals("USER"))
            return true;

            // Si on veut verifier des droits d'acces pour une perdonne connectee
        else {

            if (role.equals("SUPER") && superviseur != null)
                return true;

            // Si le role cherche est administrateur
            if (role.equals("ADMIN") && superviseur != null) {
                return (superviseur instanceof Administrateur);
            }
        }

        return false;
    }

}