package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.AdministrateurDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class AdministrateurResources {

    public static void init() throws IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (AdministrateurDAO adminDAO = AdministrateurDAO.of()) {
            EntityTransaction transaction = adminDAO.getTransaction();
            transaction.begin();

            Administrateur admin = Administrateur.builder()
                    .email("leviathan@univ-tln.fr")
                    .build();
            admin.setPasswordHash("trempette");

            adminDAO.persist(admin);

            transaction.commit();
        }


    }

    @POST
    @Path("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void updateAdmin(Administrateur admin){
        try (AdministrateurDAO adminDAO = AdministrateurDAO.of()){
            EntityTransaction transaction = adminDAO.getTransaction();
            transaction.begin();

            adminDAO.persist(admin);

            transaction.commit();
        }
    }

}
