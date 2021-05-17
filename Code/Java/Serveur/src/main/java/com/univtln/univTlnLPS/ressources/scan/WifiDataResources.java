package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.dao.scan.WifiDataDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.model.scan.WifiData;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.SecurityContext;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class WifiDataResources {

    public static void init() throws IllegalArgumentException {

    }

    @PUT
    @Path("wifis")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public WifiData addWifiData(WifiData wifidata) throws IllegalArgumentException {
        if (wifidata.getId() != 0) throw new IllegalArgumentException();

        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            EntityTransaction transaction = wifiDataDAO.getTransaction();

            transaction.begin();
            wifiDataDAO.persist(wifidata);

            transaction.commit();
        }
        return wifidata;
    }

    @POST
    @Path("wifis/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public WifiData updateWifiData(@Context SecurityContext securityContext, @PathParam("id") long id, WifiData wifidata) throws NotFoundException, IllegalArgumentException {

        // TODO check super has the rights to modify this wifi
        if (wifidata.getId() != id && id == 0) throw new IllegalArgumentException();


        // We check that super has the rights to modify this wifi (admin or supers wifiData creator)
        if(!(securityContext.getUserPrincipal() instanceof Administrateur)) {

            ScanData scanData = wifidata.getScanData();
            if(scanData == null) throw new NotFoundException();

            Superviseur superviseur = scanData.getSuperviseur();
            if(superviseur == null) throw new NotFoundException();

            if (!(securityContext.getUserPrincipal() ==  superviseur))
                throw new IllegalArgumentException();
        }

        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            EntityTransaction transaction = wifiDataDAO.getTransaction();

            transaction.begin();

            if( wifiDataDAO.find(id) == null) throw new NotFoundException();

            wifiDataDAO.persist(wifidata);

            transaction.commit();
        }
        return wifidata;
    }

    @GET
    @Path("wifis/{id}")
    public WifiData getWifiData(@PathParam("id") long id) throws NotFoundException {
        WifiData wifiData;
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {

            wifiData = wifiDataDAO.find(id);
            if( wifiData == null) throw new NotFoundException();

        }

        return wifiData;
    }

    @GET
    @Path("wifis/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getWifiDataSize() {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {

            return wifiDataDAO.findAll().size();

        }
    }

    @DELETE
    @Path("wifis/{id}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public String removeWifiData(@Context SecurityContext securityContext, @PathParam("id") long id) throws NotFoundException, IllegalArgumentException {

        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {

            if(id == 0) throw new IllegalArgumentException();

            WifiData wifiData = wifiDataDAO.find(id);
            if( wifiData == null) throw new NotFoundException();

            // We check that super has the rights to modify this wifi
            // On verifie que l'utilisateur soit admin ou le superviseur du wifi du scan
            if(!(securityContext.getUserPrincipal() instanceof Administrateur)) {

                ScanData scanData = wifiData.getScanData();
                if(scanData == null) throw new NotFoundException();

                Superviseur superviseur = scanData.getSuperviseur();
                if(superviseur == null) throw new NotFoundException();

                if (!(securityContext.getUserPrincipal() ==  superviseur))
                    throw new IllegalArgumentException();
            }

            EntityTransaction transaction = wifiDataDAO.getTransaction();

            transaction.begin();

            wifiDataDAO.remove(wifiData);

            transaction.commit();
        }

        return "success";
    }

    @DELETE
    @Path("wifis")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deleteWifiData() {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            wifiDataDAO.deleteAll();
        }

        return "success";
    }
}
