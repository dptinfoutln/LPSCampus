package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.dao.scan.ScanDataDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;

import java.util.*;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class ScanDataResources {

    public static void init() throws IllegalArgumentException {
        ScanData scanData = ScanData.builder().infoScan("").wifiList(new HashSet<>()).build();

        try (ScanDataDAO scanDataDao = ScanDataDAO.of()) {
            EntityTransaction transaction = scanDataDao.getTransaction();

            transaction.begin();
            scanDataDao.persist(scanData);

            transaction.commit();
        }
    }

    @PUT
    @Path("scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public ScanData addScanData(ScanData scandata) throws IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            EntityTransaction transaction = scanDataDAO.getTransaction();

            transaction.begin();
            scanDataDAO.persist(scandata);

            transaction.commit();
        }
        return scandata;
    }

    @POST
    @Path("scans/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public ScanData updateScanData(@PathParam("id") long id, ScanData scandata) throws NotFoundException, IllegalArgumentException {
        if (scandata.getId() != id) throw new IllegalArgumentException();

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            EntityTransaction transaction = scanDataDAO.getTransaction();

            transaction.begin();

            if( scanDataDAO.find(id) == null) throw new NotFoundException();

            scanDataDAO.persist(scandata);

            transaction.commit();
        }
        return scandata;
    }

    @GET
    @Path("scans/{id}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public ScanData getScanData(@PathParam("id") long id) throws NotFoundException {
        ScanData scanData;
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            scanData = scanDataDAO.find(id);
            if( scanData == null) throw new NotFoundException();
        }
        return scanData;
    }

    @GET
    @Path("scans/size")
    public int getScanDataSize() {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            return scanDataDAO.findAll().size();

        }
    }

    @GET
    @Path("superviseurs/{id}/scans")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public int getScanDataBySupByPieceSize(@PathParam("id") long id,
                                                       @QueryParam("idPiece") long idPiece) throws NotFoundException {

        int size;
        // On recupere le superviseur
        Superviseur superviseur;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            superviseur = superviseurDAO.find(id);
            if( superviseur == null) throw new NotFoundException();

        }

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if( piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            size = scanDataDAO.findBySuperAndPiece(superviseur, piece).size();
        }

        return size;
    }

    @GET
    @Path("superviseurs/scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public int getOwnScanDataByPieceSize(Superviseur superviseur,
                                                     @QueryParam("idPiece") long idPiece) throws NotFoundException {

        int size;

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if (piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            size = scanDataDAO.findBySuperAndPiece(superviseur, piece).size();
        }

        return size;
    }

    @GET
    @Path("superviseurs/{id}/scans")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Map<Long, ScanData> getScanDataBySupByPiece(@PathParam("id") long id,
                                                  @QueryParam("idPiece") long idPiece) throws NotFoundException {
        Map<Long, ScanData> map;

        // On recupere le superviseur
        Superviseur superviseur;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            superviseur = superviseurDAO.find(id);
            if( superviseur == null) throw new NotFoundException();

        }

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if( piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            map = scanDataDAO.findBySuperAndPiece(superviseur, piece).stream()
                    .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));
        }

        return map;
    }

    @GET
    @Path("superviseurs/scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public Map<Long, ScanData> getOwnScanDataByPiece(Superviseur superviseur,
                                                     @QueryParam("idPiece") long idPiece) throws NotFoundException {

        Map<Long, ScanData> map;

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if (piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            map = scanDataDAO.findBySuperAndPiece(superviseur, piece).stream()
                    .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));
        }

        return map;
    }

    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    @DELETE
    @Path("scans/{id}")
    public void removeScanData(@PathParam("id") long id) throws NotFoundException {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            EntityTransaction transaction = scanDataDAO.getTransaction();

            transaction.begin();
            ScanData scanData = scanDataDAO.find(id);
            if( scanData == null) throw new NotFoundException();
            scanDataDAO.remove(scanData);

            transaction.commit();
        }
    }

    @DELETE
    @Path("scans")
    public void deleteScanDatas() {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            scanDataDAO.deleteAll();
        }
    }
}
