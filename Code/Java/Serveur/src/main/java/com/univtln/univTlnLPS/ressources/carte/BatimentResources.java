package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Batiment resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class BatimentResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void init() throws IllegalArgumentException {
        Campus campus;
        try (CampusDAO campusDAO = CampusDAO.of()) {
            campus = campusDAO.findByName("tln").get(0);
        }
        Batiment bat = Batiment.builder().position_x(0).position_y(0).name("U")
                .etageList(new HashSet<>()).campus(campus).build();

        try (BatimentDAO batDAO = BatimentDAO.of()) {
            EntityTransaction transaction = batDAO.getTransaction();
            transaction.begin();

            batDAO.persist(bat);

            transaction.commit();
        }
    }

    /**
     * Add batiment string.
     *
     * @param batiment the batiment
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("batiments")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String addBatiment(Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();

        if (batiment.getCampus() == null) throw new IllegalArgumentException();
        Campus camp = CampusDAO.of().find(batiment.getCampus().getId());

        if (camp == null) throw new IllegalArgumentException();

        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            if (!batimentDAO.findByName(batiment.getName()).isEmpty())
                return "WARNING: Un batiment du même nom existe déjà";

            EntityTransaction transaction = batimentDAO.getTransaction();

            transaction.begin();

            batiment.setCampus(camp);
            batimentDAO.persist(batiment);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Update batiment batiment.
     *
     * @param id       the id
     * @param batiment the batiment
     * @return the batiment
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    @POST
    @Path("batiments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Batiment updateBatiment(@PathParam("id") long id, Batiment batiment) throws NotFoundException, IllegalArgumentException {
        if (batiment.getId() != id) throw new IllegalArgumentException();

        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            EntityTransaction transaction = batimentDAO.getTransaction();

            transaction.begin();

            if( batimentDAO.find(id) == null) throw new NotFoundException();

            batimentDAO.persist(batiment);

            transaction.commit();
        }
        return batiment;
    }

    /**
     * Gets batiment.
     *
     * @param id the id
     * @return the batiment
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("batiments/{id}")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public Batiment getBatiment(@PathParam("id") long id) throws NotFoundException {
        Batiment batiment;
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {

            batiment = batimentDAO.find(id);
            if( batiment == null) throw new NotFoundException();
        }
        return batiment;
    }

    /**
     * Gets batiment size.
     *
     * @return the batiment size
     */
    @GET
    @Path("batiments/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public int getBatimentSize() {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {

            return batimentDAO.findAll().size();

        }
    }

    /**
     * Gets batiments.
     *
     * @return the batiments
     */
    @GET
    @Path("batiments")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public Map<Long, Batiment> getBatiments() {
        Map<Long, Batiment> map;

        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            map = batimentDAO.findAll().stream()
                    .collect(Collectors.toMap(Batiment::getId, batiment -> batiment));
        }
        return map ;
    }

    /**
     * Remove batiment string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("batiments/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String removeBatiment(@PathParam("id") long id) throws NotFoundException {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            EntityTransaction transaction = batimentDAO.getTransaction();

            transaction.begin();
            Batiment batiment = batimentDAO.find(id);
            if( batiment == null) throw new NotFoundException();
            batimentDAO.safeRemove(batiment);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Delete batiments string.
     *
     * @return the string
     */
    @DELETE
    @Path("batiments")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deleteBatiments() {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            batimentDAO.deleteAll();
        }
        return "success";
    }

}
