package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.HashSet;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class ScanDataResources {


    private static long lastId = 0;

    final MutableLongObjectMap<ScanData> scandatas =
            LongObjectMaps.mutable.empty();


    @PUT
    @Path("scans/init")
    public void init() throws IllegalArgumentException {
        ScanData.builder().infoScan("").wifiList(new HashSet<>()).build();
    }

    @PUT
    @Path("scans")
    @Consumes(MediaType.APPLICATION_JSON)
    public ScanData addScanData(ScanData scandata) throws IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(++lastId);
        scandatas.put(scandata.getId(), scandata);
        return scandata;
    }

    @POST
    @Path("scans/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ScanData updateScanData(@PathParam("id") long id, ScanData scandata) throws NotFoundException, IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(id);
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.put(id, scandata);
        return scandata;
    }

    @DELETE
    @Path("scans/{id}")
    public void removeScanData(@PathParam("id") long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.remove(id);
    }

    @GET
    @Path("scans/{id}")
    public ScanData getScanData(@PathParam("id") long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        return scandatas.get(id);
    }

    @GET
    @Path("scans/size")
    public int getScanDataSize() {
        return scandatas.size();
    }

    @DELETE
    @Path("scans")
    public void deleteScanDatas() {
        scandatas.clear();
        lastId = 0;
    }

    /* TODO: finish get scans by piece_id
    @GET
    @Path("pieces/{id}/scans")
    public ScanData getScanDataByPiece(@PathParam("id") long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        return scandatas.get(id);
    }*/
}
