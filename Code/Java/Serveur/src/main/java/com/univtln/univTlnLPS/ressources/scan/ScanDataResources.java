package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class ScanDataResources {


    private static long lastId = 0;

    private static final MutableLongObjectMap<ScanData> scandatas =
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

    @GET
    @Path("pieces/{id}/scans")
    public Map<Long, ScanData> getScanDataByPiece(@PathParam("id") long id) throws NotFoundException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");
        Piece p = webResource.path("LaGarde/pieces/"+id)
                .request().get(Piece.class);

        Map<Long, ScanData> map = p.getScanList().stream()
                .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));

        return map;
    }
}
