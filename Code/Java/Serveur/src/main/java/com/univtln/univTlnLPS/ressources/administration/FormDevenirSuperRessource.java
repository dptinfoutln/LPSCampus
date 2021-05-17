package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.dao.administration.FormDevenirSuperDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.model.administration.EmailPass;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class FormDevenirSuperRessource {

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
    @Path("forms")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, FormDevenirSuper> getForms() throws NotFoundException {
        try (FormDevenirSuperDAO formDevenirSuperDAO = FormDevenirSuperDAO.of()) {
            return formDevenirSuperDAO.findAll().stream()
                    .collect(Collectors.toMap(FormDevenirSuper::getId, form -> form));
        }
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
