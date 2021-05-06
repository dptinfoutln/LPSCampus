package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.net.server.LPSServer;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class SuperviseurResources {
    private static long lastId = 0;

    final MutableLongObjectMap<Superviseur> superviseurs = LongObjectMaps.mutable.empty();

    @POST
    @Path("connexion")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public String connexion(@Context SecurityContext securityContext) {
        if (securityContext.isSecure() && securityContext.getUserPrincipal() instanceof Superviseur) {
            Superviseur superviseur = (Superviseur) securityContext.getUserPrincipal();
            return Jwts.builder()
                    .setIssuer("sample-jaxrs")
                    .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .setSubject(superviseur.getEmail())
                    .setExpiration(Date.from(LocalDateTime.now().plus(15, ChronoUnit.MINUTES).atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(LPSServer.KEY).compact();
        }
        throw new WebApplicationException(new AuthenticationException());
    }


    @PUT
    @Path("superviseurs/init")
    public void init() throws IllegalArgumentException {

    }

    // add delete update

    @PUT
    @Path("superviseurs")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Superviseur addSuperviseur(Superviseur superviseur) throws IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(++lastId);
        superviseurs.put(superviseur.getId(), superviseur);
        return superviseur;
    }

    @POST
    @Path("superviseurs/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Superviseur updateSuperviseur(@PathParam("id") long id, Superviseur superviseur) throws NotFoundException, IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(id);
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.put(id, superviseur);
        return superviseur;
    }

    @DELETE
    @Path("superviseurs/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeSuperviseur(@PathParam("id") long id) throws NotFoundException {
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.remove(id);
    }


}
