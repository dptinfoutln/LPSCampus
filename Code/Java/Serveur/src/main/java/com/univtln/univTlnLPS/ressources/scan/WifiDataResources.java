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

/**
 * The type Wifi data resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class WifiDataResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static void init() throws IllegalArgumentException {

    }

    /**
     * Add wifi data wifi data.
     *
     * @param wifidata the wifidata
     * @return the wifi data
     * @throws IllegalArgumentException the illegal argument exception
     */
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

    /**
     * Update wifi data wifi data.
     *
     * @param securityContext the security context
     * @param id              the id
     * @param wifidata        the wifidata
     * @return the wifi data
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
    @POST
    @Path("wifis/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public WifiData updateWifiData(@Context SecurityContext securityContext, @PathParam("id") long id, WifiData wifidata) throws NotFoundException, IllegalArgumentException {

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

    /**
     * Gets wifi data.
     *
     * @param id the id
     * @return the wifi data
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Gets wifi data size.
     *
     * @return the wifi data size
     */
    @GET
    @Path("wifis/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getWifiDataSize() {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {

            return wifiDataDAO.findAll().size();

        }
    }

    /**
     * Remove wifi data string.
     *
     * @param securityContext the security context
     * @param id              the id
     * @return the string
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
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

    /**
     * Delete wifi data string.
     *
     * @return the string
     */
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
