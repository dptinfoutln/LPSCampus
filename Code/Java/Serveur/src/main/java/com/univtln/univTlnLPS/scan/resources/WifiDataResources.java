package com.univtln.univTlnLPS.scan.resources;

import com.univtln.univTlnLPS.scan.model.WifiData;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class WifiDataResources {


    private static long lastId = 0;

    final MutableLongObjectMap<com.univtln.univTlnLPS.scan.model.WifiData> wifidatas =
            LongObjectMaps.mutable.empty();


    @PUT
    @Path("wifis/init")
    public void init() throws IllegalArgumentException {
        WifiData.builder().BSSID("").build();
    }

    // add delete update

    @PUT
    @Path("wifis")
    @Consumes(MediaType.APPLICATION_JSON)
    public WifiData addWifiData(WifiData wifidata) throws IllegalArgumentException {
        if (wifidata.getId() != 0) throw new IllegalArgumentException();
        wifidata.setId(++lastId);
        wifidatas.put(wifidata.getId(), wifidata);
        return wifidata;
    }

    @POST
    @Path("wifis/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public WifiData updateWifiData(@PathParam("id") long id, WifiData wifidata) throws NotFoundException, IllegalArgumentException {
        if (wifidata.getId() != 0) throw new IllegalArgumentException();
        wifidata.setId(id);;
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        wifidatas.put(id, wifidata);
        return wifidata;
    }

    @DELETE
    @Path("wifis/{id}")
    public void removeWifiData(@PathParam("id") long id) throws NotFoundException {
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        wifidatas.remove(id);
    }

    @GET
    @Path("wifis/{id}")
    public WifiData getWifiData(@PathParam("id") long id) throws NotFoundException {
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        return wifidatas.get(id);
    }

    @GET
    @Path("wifis/size")
    public int getWifiDataSize() {
        return wifidatas.size();
    }

    @DELETE
    @Path("wifis")
    public void deleteWifiData() {
        wifidatas.clear();
        lastId = 0;
    }
}
