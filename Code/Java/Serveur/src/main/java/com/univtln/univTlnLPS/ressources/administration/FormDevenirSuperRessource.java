package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.FormDevenirSuperDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.EmailPass;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Form devenir super ressource.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class FormDevenirSuperRessource {

    /**
     * Add form string.
     *
     * @param cred the cred
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvalidKeySpecException  the invalid key spec exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @PUT
    @Path("forms")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addForm(EmailPass cred) throws IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {
        FormDevenirSuper form = FormDevenirSuper.builder().email(cred.getEmail()).id(0).build();
        form.setPasswordHash(cred.getPassword());

        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            if (!formDevenirSuperDAO.findByEmail(form.getEmail()).isEmpty() ||
                !SuperviseurDAO.of().findByEmail(form.getEmail()).isEmpty())
                throw new IllegalArgumentException();

            EntityTransaction transaction = formDevenirSuperDAO.getTransaction();

            transaction.begin();

            formDevenirSuperDAO.persist(form);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Gets form.
     *
     * @param id the id
     * @return the form
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("forms/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public FormDevenirSuper getForm(@PathParam("id") long id) throws NotFoundException {
        FormDevenirSuper form;
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {

            form = formDevenirSuperDAO.find(id);
            if( form == null) throw new NotFoundException();
        }
        return form;
    }

    /**
     * Gets forms.
     *
     * @return the forms
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("forms")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, FormDevenirSuper> getForms() throws NotFoundException {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            return formDevenirSuperDAO.findAll().stream()
                    .collect(Collectors.toMap(FormDevenirSuper::getId, form -> form));
        }
    }

    /**
     * Gets form size.
     *
     * @return the form size
     */
    @GET
    @Path("forms/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getFormSize() {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {

            return formDevenirSuperDAO.findAll().size();

        }
    }

    /**
     * Remove form string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("forms/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String removeForm(@PathParam("id") long id) throws NotFoundException {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            EntityTransaction transaction = formDevenirSuperDAO.getTransaction();

            transaction.begin();
            FormDevenirSuper form = formDevenirSuperDAO.find(id);
            if( form == null) throw new NotFoundException();
            formDevenirSuperDAO.remove(form);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Delete forms string.
     *
     * @return the string
     */
    @DELETE
    @Path("forms")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deleteForms() {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            formDevenirSuperDAO.deleteAll();
        }

        return "success";
    }


}
