package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.net.server.LPSServer;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TransactionRequiredException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class SuperviseurResources {

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


    public static void init() throws IllegalArgumentException {
        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            try {
                for (int i = 1; i <= 5; i++) {
                    Superviseur superviseur = Superviseur.builder()
                            .email("super" + i + "@univ-tln.fr").scanList(new HashSet<>()).build();
                    superviseur.setPasswordHash("password");

                    superDAO.persist(superviseur);
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }


            transaction.commit();
        }
    }

    // add delete update

    @PUT
    @Path("superviseurs")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Superviseur addSuperviseur(Superviseur superviseur) throws IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            superDAO.persist(superviseur);

            transaction.commit();
        }

        return superviseur;
    }

    @POST
    @Path("superviseurs/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Superviseur updateSuperviseur(@PathParam("id") long id, Superviseur superviseur) throws NotFoundException, IllegalArgumentException {
        if (superviseur.getId() != id) throw new IllegalArgumentException();

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            if (superDAO.find(id) == null) throw new NotFoundException();

            superDAO.persist(superviseur);

            transaction.commit();
        }
        return superviseur;
    }

    /*@DELETE
    @Path("superviseurs/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeSuperviseur(@PathParam("id") long id) throws NotFoundException {
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.remove(id);
    }*/

    @DELETE
    @Path("superviseurs/me")
    @RolesAllowed({"SUPER"})
    @JWTAuth
    public void removeSuperviseur(@Context SecurityContext securityContext) throws NotFoundException {
        Superviseur superviseur = (Superviseur)securityContext.getUserPrincipal();

        log.info(superviseur.toString());

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {

            superviseur = superDAO.findByEmail(superviseur.getEmail()).get(0);
            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            superDAO.remove(superviseur);

            transaction.commit();
        }
    }

    @GET
    @Path("superviseurs/me/role")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public String getSuperviseurRole(@Context SecurityContext securityContext) throws NotFoundException {
        Superviseur superviseur = (Superviseur)securityContext.getUserPrincipal();

        if (superviseur instanceof Administrateur)
            return "ADMIN";

        return "SUPER";
    }
}
