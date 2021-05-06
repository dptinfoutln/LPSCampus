package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.dao.scan.WifiDataDAO;
import com.univtln.univTlnLPS.model.scan.WifiData;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class WifiDataResources {

    public static void init() throws IllegalArgumentException {
        WifiData wifi = WifiData.builder().BSSID("test").build();

        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            EntityTransaction transaction = wifiDataDAO.getTransaction();

            transaction.begin();
            wifiDataDAO.persist(wifi);

            transaction.commit();
        }
    }

    // add delete update

    @PUT
    @Path("wifis")
    @Consumes(MediaType.APPLICATION_JSON)
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
    public WifiData updateWifiData(@PathParam("id") long id, WifiData wifidata) throws NotFoundException, IllegalArgumentException {
        if (wifidata.getId() != id) throw new IllegalArgumentException();

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
    public int getWifiDataSize() {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {

            return wifiDataDAO.findAll().size();

        }
    }

    @DELETE
    @Path("wifis/{id}")
    public void removeWifiData(@PathParam("id") long id) throws NotFoundException {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            EntityTransaction transaction = wifiDataDAO.getTransaction();

            transaction.begin();
            WifiData wifiData = wifiDataDAO.find(id);
            if( wifiData == null) throw new NotFoundException();
            wifiDataDAO.remove(wifiData);

            transaction.commit();
        }
    }

    @DELETE
    @Path("wifis")
    public void deleteWifiData() {
        try (WifiDataDAO wifiDataDAO = WifiDataDAO.of()) {
            wifiDataDAO.deleteAll();
        }
    }
}
