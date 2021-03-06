package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.UtilisateurDAO;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;


/**
 * The type Utilisateur resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class UtilisateurResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void init() throws IllegalArgumentException {

    }


    // add delete update

    /**
     * Add utilisateur string.
     *
     * @param utilisateur the utilisateur
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("utilisateurs")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addUtilisateur(Utilisateur utilisateur) throws IllegalArgumentException {
        if (utilisateur.getId() != 0) throw new IllegalArgumentException();

        try (UtilisateurDAO userDAO = UtilisateurDAO.of()){
            EntityTransaction transaction = userDAO.getTransaction();
            transaction.begin();

            userDAO.persist(utilisateur);

            transaction.commit();
        }
        return "success";
    }

    /**
     * Remove utilisateur string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("utilisateurs/{id}")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public String removeUtilisateur(@PathParam("id") long id) throws NotFoundException {
        try (UtilisateurDAO userDAO = UtilisateurDAO.of()){
            Utilisateur user = userDAO.find(id);
            if (user == null)
                throw new NotFoundException();

            EntityTransaction transaction = userDAO.getTransaction();
            transaction.begin();

            userDAO.persist(user);

            transaction.commit();
        }

        return "success";
    }

}
