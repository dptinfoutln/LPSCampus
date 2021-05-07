package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.FormDevenirSuperDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.util.HashSet;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class FormDevenirSuperRessource {

    @PUT
    @Path("forms")
    @Consumes(MediaType.APPLICATION_JSON)
    public FormDevenirSuper addForm(FormDevenirSuper form) throws IllegalArgumentException {
        if (form.getId() != 0) throw new IllegalArgumentException();

        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            EntityTransaction transaction = formDevenirSuperDAO.getTransaction();

            transaction.begin();
            formDevenirSuperDAO.persist(form);

            transaction.commit();
        }
        return form;
    }

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

    @GET
    @Path("forms/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getFormSize() {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {

            return formDevenirSuperDAO.findAll().size();

        }
    }

    @DELETE
    @Path("forms/{id}/choice")
    @RolesAllowed("Admin")
    @JWTAuth
    public Superviseur answeredForm(@PathParam("id") long id,
                             @QueryParam("choice") String choice) throws NotFoundException {

        Superviseur superviseur = null;

        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {


            FormDevenirSuper form = formDevenirSuperDAO.find(id);

            if (form == null) throw new NotFoundException();

            if (choice.equals("accepted")) {
                superviseur = Superviseur.builder()
                        .email(form.getEmail())
                        .salt(form.getSalt())
                        .passwordHash(form.getPasswordHash())
                        .build();

                if (superviseur.getId() != 0) throw new IllegalArgumentException();

                try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
                    EntityTransaction transactionSup = superDAO.getTransaction();
                    transactionSup.begin();

                    superDAO.persist(superviseur);

                    transactionSup.commit();
                }
            }

            EntityTransaction transaction = formDevenirSuperDAO.getTransaction();
            transaction.begin();

            formDevenirSuperDAO.remove(form);

            transaction.commit();

            return superviseur;

        }
    }

    @DELETE
    @Path("forms/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeForm(@PathParam("id") long id) throws NotFoundException {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            EntityTransaction transaction = formDevenirSuperDAO.getTransaction();

            transaction.begin();
            FormDevenirSuper form = formDevenirSuperDAO.find(id);
            if( form == null) throw new NotFoundException();
            formDevenirSuperDAO.remove(form);

            transaction.commit();
        }
    }

    @DELETE
    @Path("forms")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void deleteForms() {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            formDevenirSuperDAO.deleteAll();
        }
    }


}
