package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.Map;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class EtageResources {

    public static void init() throws IllegalArgumentException {
        Etage.builder().build();
    }

    @PUT
    @Path("etages")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Etage addEtage(Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();

        try (EtageDAO etageDAO = EtageDAO.of()) {
            EntityTransaction transaction = etageDAO.getTransaction();

            transaction.begin();
            etageDAO.persist(etage);

            transaction.commit();
        }
        return etage;
    }

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

    @GET
    @Path("etages/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public int getEtageSize() {
        try (EtageDAO etageDAO = EtageDAO.of()) {

            return etageDAO.findAll().size();

        }
    }

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

    @DELETE
    @Path("etages/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void removeEtage(@PathParam("id") long id) throws NotFoundException {
        try (EtageDAO etageDAO = EtageDAO.of()) {
            EntityTransaction transaction = etageDAO.getTransaction();

            transaction.begin();
            Etage etage = etageDAO.find(id);
            if( etage == null) throw new NotFoundException();
            etageDAO.remove(etage);

            transaction.commit();
        }
    }

    @DELETE
    @Path("etages")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void deleteEtages() {
        try (EtageDAO etageDAO = EtageDAO.of()) {
            etageDAO.deleteAll();
        }
    }

}