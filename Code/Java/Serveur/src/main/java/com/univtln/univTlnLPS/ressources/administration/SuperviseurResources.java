package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.FormDevenirSuperDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.net.server.LPSServer;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;
import jakarta.ws.rs.*;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Superviseur resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class SuperviseurResources {

    /**
     * Connexion string.
     *
     * @param securityContext the security context
     * @return the string
     */
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


    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
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

            } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
                e.printStackTrace();
            }


            transaction.commit();
        }
    }

    // add delete update

    /**
     * Add superviseur.
     *
     * @param id the id
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("superviseurs/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void addSuperviseur(@PathParam("id") long id) throws IllegalArgumentException {
        FormDevenirSuper form;
        EntityTransaction transaction;

        try (FormDevenirSuperDAO formDAO = FormDevenirSuperDAO.of()){
            form = formDAO.find(id);
            if (form == null)
                throw new NotFoundException();

            Superviseur superviseur = Superviseur.builder().email(form.getEmail())
                    .passwordHash(form.getPasswordHash())
                    .id(0)
                    .random(form.getRandom())
                    .salt(form.getSalt()).build();

            try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
                transaction = superDAO.getTransaction();
                transaction.begin();

                superDAO.persist(superviseur);

                transaction.commit();
            }

            transaction = formDAO.getTransaction();
            transaction.begin();

            formDAO.remove(form);

            transaction.commit();
        }
    }

    /**
     * Update superviseur superviseur.
     *
     * @param id          the id
     * @param superviseur the superviseur
     * @return the superviseur
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
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

    /**
     * Remove superviseur.
     *
     * @param id the id
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("superviseurs/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeSuperviseur(@PathParam("id") long id) throws NotFoundException {
        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            Superviseur superviseur = superDAO.find(id);

            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            superDAO.remove(superviseur);

            transaction.commit();
        }
    }

    /**
     * Gets superviseurs.
     *
     * @return the superviseurs
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("superviseurs")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, Superviseur> getSuperviseurs() throws NotFoundException {
        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            return superDAO.findAll().stream()
                    .collect(Collectors.toMap(Superviseur::getId, superviseur -> superviseur));
        }
    }

    /**
     * Gets superviseur.
     *
     * @param securityContext the security context
     * @return the superviseur
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("superviseurs/me")
    @RolesAllowed({"SUPER"})
    @JWTAuth
    public Superviseur getSuperviseur(@Context SecurityContext securityContext) throws NotFoundException {
        return (Superviseur)securityContext.getUserPrincipal();
    }

    /**
     * Remove superviseur.
     *
     * @param securityContext the security context
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("superviseurs/me")
    @RolesAllowed({"SUPER"})
    @JWTAuth
    public void removeSuperviseur(@Context SecurityContext securityContext) throws NotFoundException {
        Superviseur superviseur = (Superviseur)securityContext.getUserPrincipal();

        log.info(superviseur.toString() + "supprim??");

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {

            superviseur = superDAO.findByEmail(superviseur.getEmail()).get(0);
            EntityTransaction transaction = superDAO.getTransaction();
            transaction.begin();

            superDAO.remove(superviseur);

            transaction.commit();
        }
    }

    /**
     * Gets superviseur role.
     *
     * @param securityContext the security context
     * @return the superviseur role
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Change superviseur login.
     *
     * @param securityContext the security context
     * @param newLogin        the new login
     * @throws NotFoundException the not found exception
     */
    @POST
    @Path("superviseurs/me/login")
    @RolesAllowed({"SUPER", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTAuth
    public void changeSuperviseurLogin(@Context SecurityContext securityContext,
                                         String newLogin) throws NotFoundException {
        Superviseur superviseur = (Superviseur)securityContext.getUserPrincipal();

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            EntityTransaction et = superDAO.getTransaction();
            et.begin();

            superviseur = superDAO.findByEmail(superviseur.getEmail()).get(0);
            superviseur.setEmail(newLogin.replace("\n", "").replace(" ", ""));
            superDAO.persist(superviseur);

            et.commit();
        }
    }

    /**
     * Change superviseur mdp.
     *
     * @param securityContext the security context
     * @param newMdp          the new mdp
     * @throws NotFoundException the not found exception
     */
    @POST
    @Path("superviseurs/me/mdp")
    @RolesAllowed({"SUPER", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTAuth
    public void changeSuperviseurMdp(@Context SecurityContext securityContext,
                                       String newMdp) throws NotFoundException {
        Superviseur superviseur = (Superviseur)securityContext.getUserPrincipal();

        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            EntityTransaction et = superDAO.getTransaction();
            et.begin();

            superviseur = superDAO.findByEmail(superviseur.getEmail()).get(0);
            superviseur.setPasswordHash(newMdp.replace("\n", "").replace(" ", ""));
            superDAO.persist(superviseur);

            et.commit();
        } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
