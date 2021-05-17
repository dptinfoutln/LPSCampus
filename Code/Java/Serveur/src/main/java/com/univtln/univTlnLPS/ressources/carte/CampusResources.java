package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.administration.AdministrateurDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Campus;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class CampusResources {

    public static void init() {
        Administrateur admin;
        try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
            admin = (Administrateur) superDAO.findByEmail("leviathan@univ-tln.fr").get(0);
        }

        try (CampusDAO campDao = CampusDAO.of()){
            EntityTransaction transaction = campDao.getTransaction();
            transaction.begin();

            Campus camp = new Campus("tln", "plan", new HashSet<>(), 0,
                    admin);
            campDao.persist(camp);

            transaction.commit();
        }
    }

    @PUT
    @Path("campus")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String addCampus(Campus camp, @Context SecurityContext context) throws IllegalArgumentException {
        try (CampusDAO campDao = CampusDAO.of()) {
            if(camp.getId() != 0 || !campDao.findByName(camp.getName()).isEmpty())
                throw new IllegalArgumentException();

            EntityTransaction transaction = campDao.getTransaction();
            transaction.begin();

            camp.setAdministrateur(AdministrateurDAO.of()
                    .find( ((Superviseur)context.getUserPrincipal()).getId()) );
            campDao.persist(camp);

            transaction.commit();
        }

        return "success";
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
    public String delCampus(@PathParam("id") long id) {
        try (CampusDAO campusDAO = CampusDAO.of()) {
            EntityTransaction transaction = campusDAO.getTransaction();

            transaction.begin();
            Campus campus = campusDAO.find(id);
            if( campus == null) throw new NotFoundException();
            campusDAO.remove(campus);

            transaction.commit();
        }

        return "success";
    }

}
