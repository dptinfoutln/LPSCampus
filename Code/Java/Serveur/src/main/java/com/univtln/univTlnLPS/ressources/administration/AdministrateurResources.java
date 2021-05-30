package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.common.LPSModele;
import com.univtln.univTlnLPS.dao.administration.AdministrateurDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * The type Administrateur resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class AdministrateurResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvalidKeySpecException  the invalid key spec exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static void init() throws IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (AdministrateurDAO adminDAO = AdministrateurDAO.of()) {
            EntityTransaction transaction = adminDAO.getTransaction();
            transaction.begin();

            Administrateur admin = Administrateur.builder()
                    .email("admin@univ-tln.fr")
                    .build();
            admin.setPasswordHash("univtlnlps");

            adminDAO.persist(admin);

            transaction.commit();
        }
    }

    @PUT
    @Path("flush")
    public static void flush() throws InvalidKeySpecException, NoSuchAlgorithmException {
        LPSModele.deleteAll();

        init();
    }

    /**
     * Update admin string.
     *
     * @param admin the admin
     * @return success
     */
    @POST
    @Path("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String updateAdmin(Administrateur admin) {
        try (AdministrateurDAO adminDAO = AdministrateurDAO.of()){
            EntityTransaction transaction = adminDAO.getTransaction();
            transaction.begin();

            adminDAO.persist(admin);

            transaction.commit();
        }

        return "success";
    }

    @PUT
    @Path("flush")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String flush() throws InvalidKeySpecException, NoSuchAlgorithmException {
        LPSModele.deleteAll();
        init();

        return "success";
    }
}
