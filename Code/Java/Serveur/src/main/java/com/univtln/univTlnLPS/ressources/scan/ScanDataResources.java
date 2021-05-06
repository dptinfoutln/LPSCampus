package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.scan.ScanDataDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.*;
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
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public ScanData addScanData(ScanData scandata) throws IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(++lastId);
        scandatas.put(scandata.getId(), scandata);
        return scandata;
    }

    @POST
    @Path("scans/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public ScanData updateScanData(@PathParam("id") long id, ScanData scandata) throws NotFoundException, IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(id);
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.put(id, scandata);
        return scandata;
    }

    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    @DELETE
    @Path("scans/{id}")
    public void removeScanData(@PathParam("id") long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.remove(id);
    }

    @GET
    @Path("scans/{id}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
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
    @Path("superviseurs/{id}/scans")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Map<Long, ScanData> getScanDataBySupByPiece(@PathParam("id") long id,
                                                  @QueryParam("idPiece") long idPiece) throws NotFoundException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");
        Piece p = webResource.path("LaGarde/pieces/"+idPiece)
                .request().get(Piece.class);

        Map<Long, ScanData> map = p.getScanList().stream()
                .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));

        return map;
    }

    @GET
    @Path("superviseurs/scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public Map<Long, ScanData> getOwnScanDataByPiece(Superviseur superviseur,
                                                     @QueryParam("idPiece") long idPiece) throws NotFoundException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");
        Piece p = webResource.path("LaGarde/pieces/"+idPiece)
                .request().get(Piece.class);

        List<ScanData> liste = new ArrayList<>();
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            liste = scanDataDAO.findBySuperAndPiece(superviseur, p);
        }

        Map<Long, ScanData> map = liste.stream()
                .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));

        return map;
    }
}
