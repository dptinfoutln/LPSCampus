package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Etage resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class EtageResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void init() throws IllegalArgumentException {
        Batiment bat;

        try (BatimentDAO batDAO = BatimentDAO.of()) {
            bat = batDAO.findByName("U").get(0);
        }

        Etage et = new Etage("plan", "U-rdc", 0, bat,
                new HashSet<>());

        try (EtageDAO etDAO = EtageDAO.of()) {
            EntityTransaction transaction = etDAO.getTransaction();
            transaction.begin();

            etDAO.persist(et);

            transaction.commit();
        }
    }

    /**
     * Add etage string.
     *
     * @param etage the etage
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("etages")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String addEtage(Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();

        if (etage.getBatiment() == null) throw new IllegalArgumentException();
        Batiment bat = BatimentDAO.of().find(etage.getBatiment().getId());

        if (bat == null) throw new IllegalArgumentException();

        try (EtageDAO etageDAO = EtageDAO.of()) {
            if (!etageDAO.findByName(etage.getName()).isEmpty())
                return "WARNING: Un ??tage du m??me nom existe d??j??";

            EntityTransaction transaction = etageDAO.getTransaction();

            transaction.begin();

            etage.setBatiment(bat);
            etageDAO.persist(etage);

            transaction.commit();
        }
        return "success";
    }

    /**
     * Update etage etage.
     *
     * @param id    the id
     * @param etage the etage
     * @return the etage
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    @POST
    @Path("etages/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Etage updateEtage(@PathParam("id") long id, Etage etage) throws NotFoundException, IllegalArgumentException {
        if (etage.getId() != id) throw new IllegalArgumentException();

        try (EtageDAO etageDAO = EtageDAO.of()) {
            EntityTransaction transaction = etageDAO.getTransaction();

            transaction.begin();

            if( etageDAO.find(id) == null) throw new NotFoundException();

            etageDAO.persist(etage);

            transaction.commit();
        }
        return etage;
    }

    /**
     * Gets etage.
     *
     * @param id the id
     * @return the etage
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("etages/{id}")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public Etage getEtage(@PathParam("id") long id) throws NotFoundException {
        Etage etage;
        try (EtageDAO etageDAO = EtageDAO.of()) {

            etage = etageDAO.find(id);
            if( etage == null) throw new NotFoundException();
        }
        return etage;
    }

    /**
     * Gets etage size.
     *
     * @return the etage size
     */
    @GET
    @Path("etages/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public int getEtageSize() {
        try (EtageDAO etageDAO = EtageDAO.of()) {

            return etageDAO.findAll().size();

        }
    }

    /**
     * Gets etages.
     *
     * @return the etages
     */
    @GET
    @Path("etages")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public Map<Long, Etage> getEtages() {
        Map<Long, Etage> map;

        try (EtageDAO etageDAO = EtageDAO.of()) {

            map = etageDAO.findAll().stream()
                    .collect(Collectors.toMap(Etage::getId, etage -> etage));

        }
        return map ;
    }

    /**
     * Remove etage string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("etages/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String removeEtage(@PathParam("id") long id) throws NotFoundException {
        try (EtageDAO etageDAO = EtageDAO.of()) {
            EntityTransaction transaction = etageDAO.getTransaction();

            transaction.begin();
            Etage etage = etageDAO.find(id);
            if( etage == null) throw new NotFoundException();
            etageDAO.safeRemove(etage);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Delete etages string.
     *
     * @return the string
     */
    @DELETE
    @Path("etages")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deleteEtages() {
        try (EtageDAO etageDAO = EtageDAO.of()) {
            etageDAO.deleteAll();
        }

        return "success";
    }

}