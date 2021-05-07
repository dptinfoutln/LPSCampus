package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class CampusResources {

    public static void init() {
        try (CampusDAO campDao = CampusDAO.of()){
            if (campDao.findByName("tln").isEmpty()) {
                EntityTransaction transaction = campDao.getTransaction();
                transaction.begin();

                Campus camp = new Campus("tln", "plan", new HashSet<>(), 0, null);
                campDao.persist(camp);

                transaction.commit();
            }

        }
    }

    @PUT
    @Path("campus")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void addCampus(Campus camp) throws IllegalArgumentException {
        try (CampusDAO campDao = CampusDAO.of()){
            if(camp.getId() != 0 || !campDao.findByName(camp.getName()).isEmpty())
                throw new IllegalArgumentException();

            EntityTransaction transaction = campDao.getTransaction();
            transaction.begin();

            campDao.persist(camp);

            transaction.commit();
        }
    }

    @GET
    @Path("campus")
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public Map<Long, Campus> getCampus() {
        return CampusDAO.of().findAll().stream()
                .collect(Collectors.toMap(Campus::getId, campus -> campus));
    }

    @DELETE
    @Path("campus/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void delCampus(@PathParam("id") long id) {
        try (CampusDAO campusDAO = CampusDAO.of()) {
            EntityTransaction transaction = campusDAO.getTransaction();

            transaction.begin();
            Campus campus = campusDAO.find(id);
            if( campus == null) throw new NotFoundException();
            campusDAO.remove(campus);

            transaction.commit();
        }
    }

}
