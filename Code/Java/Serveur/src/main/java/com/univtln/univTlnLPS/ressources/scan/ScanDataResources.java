package com.univtln.univTlnLPS.ressources.scan;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.dao.scan.ScanDataDAO;
import com.univtln.univTlnLPS.dao.scan.WifiDataDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Piece;
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
import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Collectors;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
@Log
public class ScanDataResources {

    public static void init() throws IllegalArgumentException {

    }

    @PUT
    @Path("scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public void addScanData(ScanData scanData,
                                @QueryParam("piece") long idPiece,
                                @Context SecurityContext securityContext) throws IllegalArgumentException {
        if (scanData.getId() != 0) throw new IllegalArgumentException();

        Superviseur superviseur = (Superviseur) securityContext.getUserPrincipal();

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            EntityTransaction transaction;

            Set<WifiData> wifiSet = scanData.getWifiList();

            scanData.setWifiList(new HashSet<>());

            try (SuperviseurDAO superDAO = SuperviseurDAO.of()) {
                transaction = scanDataDAO.getTransaction();

                transaction.begin();
                scanData.setSuperviseur(superDAO.findByEmail(superviseur.getEmail()).get(0));
                scanData.setPiece(PieceDAO.of().find(idPiece));
                scanDataDAO.persist(scanData);

                transaction.commit();
            }

            ScanData sData = scanDataDAO.find(scanData.getId());
            try (WifiDataDAO wDAO = WifiDataDAO.of()) {
                transaction = wDAO.getTransaction();

                transaction.begin();

                for (WifiData wData : wifiSet) {
                    wData.setScanData(sData);
                    wDAO.persist(wData);
                }

                transaction.commit();
            }
        }

    }

    @POST
    @Path("scans/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "SUPER"})
    @JWTAuth
    public ScanData updateScanData(@Context SecurityContext securityContext, @PathParam("id") long id, ScanData scandata) throws NotFoundException, IllegalArgumentException {
        // On verifie que l'utilisateur soit admin ou le superviseur du scan
        if(!(securityContext.getUserPrincipal() instanceof Administrateur)) {
            if (!(securityContext.getUserPrincipal() == scandata.getSuperviseur()))
                throw new IllegalArgumentException();
        }

        if (scandata.getId() != id && id == 0) throw new IllegalArgumentException();

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
    @JWTAuth
    public ScanData getScanData(@Context SecurityContext securityContext, @PathParam("id") long id) throws NotFoundException, IllegalArgumentException{
        ScanData scanData;
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            scanData = scanDataDAO.find(id);

            // On verifie que l'utilisateur soit admin ou le superviseur du scan
            if(!(securityContext.getUserPrincipal() instanceof Administrateur)) {
                if (!(securityContext.getUserPrincipal() == scanData.getSuperviseur()))
                    throw new IllegalArgumentException();
            }

            if( scanData == null) throw new NotFoundException();
        }
        return scanData;
    }

    @GET
    @Path("scans/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getScanDataSize() {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            return scanDataDAO.findAll().size();

        }
    }

    public List<Piece> getScanPiecesBySuperEF(long id) throws NotFoundException{
        Superviseur superviseur;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            superviseur = superviseurDAO.find(id);
            if (superviseur == null) throw new NotFoundException();
        }
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            return scanDataDAO.findScanPiecesBySuper(superviseur);
        }
    }

    public List<ScanData> getScanDataBySupByPieceEF( long id, long idPiece) throws NotFoundException {
        // On recupere le superviseur
        Superviseur superviseur;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            superviseur = superviseurDAO.find(id);
            if (superviseur == null) throw new NotFoundException();

        }

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if (piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            return scanDataDAO.findBySuperAndPiece(superviseur, piece);
        }
    }

    public List<ScanData> getOwnScanDataByPieceEF (SecurityContext securityContext,
                                                   long idPiece) throws NotFoundException {

        Superviseur superviseur = (Superviseur) securityContext.getUserPrincipal();

        // On recupere la piece
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(idPiece);
            if (piece == null) throw new NotFoundException();
        }

        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            return scanDataDAO.findBySuperAndPiece(superviseur, piece);
        }
    }

    @GET
    @Path("superviseurs/me/scans/pieces/size")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public int getOwnScanPiecesBySuperSize(@Context SecurityContext securityContext) throws NotFoundException {

        Superviseur superviseur = (Superviseur) securityContext.getUserPrincipal();

        return getScanPiecesBySuperEF(superviseur.getId()).size();
    }

    @GET
    @Path("superviseurs/{id}/scans/pieces/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getScanPiecesBySuperSize(@PathParam("id") long id) throws NotFoundException {

        return getScanPiecesBySuperEF(id).size();
    }

    @GET
    @Path("superviseurs/{id}/scans/size")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public int getScanDataBySupByPieceSize(@PathParam("id") long id,
                                           @QueryParam("idPiece") long idPiece) throws NotFoundException {

        return getScanDataBySupByPieceEF(id, idPiece).size();
    }

    @GET
    @Path("superviseurs/me/scans/size")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public int getOwnScanDataByPieceSize(@Context SecurityContext securityContext,
                                         @QueryParam("idPiece") long idPiece) throws NotFoundException{

        return getOwnScanDataByPieceEF(securityContext, idPiece).size();
    }

    @GET
    @Path("superviseurs/{id}/scans/pieces")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, Piece> getScanPiecesBySuper(@PathParam("id") long id) throws NotFoundException {

        return getScanPiecesBySuperEF(id).stream()
                .collect(Collectors.toMap(Piece::getId, piece -> piece));
    }

    @GET
    @Path("superviseurs/me/scans/pieces")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public Map<Long, Piece> getOwnScanPiecesBySuper(@Context SecurityContext securityContext) throws NotFoundException {

        Superviseur superviseur = (Superviseur) securityContext.getUserPrincipal();

        return getScanPiecesBySuperEF(superviseur.getId()).stream()
                .collect(Collectors.toMap(Piece::getId, piece -> piece));
    }

    @GET
    @Path("superviseurs/{id}/scans")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Map<Long, ScanData> getScanDataBySupByPiece(@PathParam("id") long id,
                                                  @QueryParam("idPiece") long idPiece) throws NotFoundException {

        return getScanDataBySupByPieceEF(id, idPiece).stream()
                    .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));
    }

    @GET
    @Path("superviseurs/me/scans")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public Map<Long, ScanData> getOwnScanDataByPiece(@Context SecurityContext securityContext,
                                                     @QueryParam("idPiece") long idPiece) throws NotFoundException {

        return getOwnScanDataByPieceEF(securityContext, idPiece).stream()
                    .collect(Collectors.toMap(ScanData::getId, scanData -> scanData));

    }

    public void removeScanEF(List<ScanData> listeScan){

        for (ScanData scanData:
                listeScan) {
            try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
                EntityTransaction transaction = scanDataDAO.getTransaction();

                transaction.begin();

                scanDataDAO.remove(scanData);

                transaction.commit();
            }
        }
    }

    @DELETE
    @Path("superviseurs/{id}/scans")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public  void removeScanDataBySupByPiece(@PathParam("id") long id,
                                                       @QueryParam("idPiece") long idPiece) throws NotFoundException {

        List<ScanData>  liste = getScanDataBySupByPieceEF(id, idPiece);
        removeScanEF(liste);
    }

    @DELETE
    @Path("superviseurs/me/scans")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public void removeOwnScanDataByPiece(@Context SecurityContext securityContext,
                                                     @QueryParam("idPiece") long idPiece) throws NotFoundException {

        List<ScanData> liste =  getOwnScanDataByPieceEF(securityContext, idPiece);
        removeScanEF(liste);
    }

    @DELETE
    @Path("scans/{id}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public void removeScanData(@Context SecurityContext securityContext, @PathParam("id") long id) throws NotFoundException, IllegalArgumentException {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {

            if(id == 0) throw new IllegalArgumentException();

            ScanData scanData = scanDataDAO.find(id);
            if( scanData == null) throw new NotFoundException();

            // On verifie que l'utilisateur soit admin ou le superviseur du scan
            if(!(securityContext.getUserPrincipal() instanceof Administrateur)) {
                if (!(securityContext.getUserPrincipal() == scanData.getSuperviseur()))
                    throw new IllegalArgumentException();
            }

            EntityTransaction transaction = scanDataDAO.getTransaction();

            transaction.begin();

            scanDataDAO.remove(scanData);

            transaction.commit();
        }
    }

    @DELETE
    @Path("scans")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public void deleteScanDatas() {
        try (ScanDataDAO scanDataDAO = ScanDataDAO.of()) {
            scanDataDAO.deleteAll();
        }
    }
}
