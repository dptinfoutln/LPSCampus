package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class BatimentResources {

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

    @PUT
    @Path("batiments")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void addBatiment(Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();

        if (batiment.getCampus() == null) throw new IllegalArgumentException();
        Campus camp = CampusDAO.of().find(batiment.getCampus().getId());

        if (camp == null) throw new IllegalArgumentException();

        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            EntityTransaction transaction = batimentDAO.getTransaction();

            transaction.begin();

            batiment.setCampus(camp);
            batimentDAO.persist(batiment);

            transaction.commit();
        }
    }

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

    @GET
    @Path("batiments/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public int getBatimentSize() {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {

            return batimentDAO.findAll().size();

        }
    }

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

    @DELETE
    @Path("batiments/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeBatiment(@PathParam("id") long id) throws NotFoundException {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            EntityTransaction transaction = batimentDAO.getTransaction();

            transaction.begin();
            Batiment batiment = batimentDAO.find(id);
            if( batiment == null) throw new NotFoundException();
            batimentDAO.remove(batiment);

            transaction.commit();
        }
    }

    @DELETE
    @Path("batiments")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void deleteBatiments() {
        try (BatimentDAO batimentDAO = BatimentDAO.of()) {
            batimentDAO.deleteAll();
        }
    }

}
