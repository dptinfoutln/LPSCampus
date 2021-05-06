package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
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
public class PieceResources {
    private static long lastId = 0;

    private static final MutableLongObjectMap<Piece> pieces = LongObjectMaps.mutable.empty();

    public static void init() throws IllegalArgumentException {
        long i;
        Batiment bat = new Batiment(0, 0, "U", new HashSet<>(), 1);
        Etage et = new Etage("plan", "rdc", 1, null, new HashSet<>());
        bat.getEtageList().add(et);
        BatimentResources.batiments.put(1, bat);
        EtageResources.etages.put(1, et);
        for(i = 0; i < 5; i++){
            Piece p = Piece.builder().build();

            Set<ScanData> scanList = new HashSet<>();
            scanList.add(ScanData.builder().id(i*2).piece(p).build());
            scanList.add(ScanData.builder().id(i*2+1).piece(p).build());

            p.setId(i);
            p.setEtage(et);
            et.getPieceList().add(p);
            p.setPosition_x((int)i);
            p.setScanList(scanList);
            p.setName("U-00"+(i+1));

            pieces.put(i, p);
        }
        lastId = 5;
    }

    // add delete update

    @PUT
    @Path("pieces")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Piece addPiece(Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();

        try (PieceDAO pieceDAO = PieceDAO.of()) {
            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();
            pieceDAO.persist(piece);

            transaction.commit();
        }
        return piece;
    }

    @POST
    @Path("pieces/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Piece updatePiece(@PathParam("id") long id, Piece piece) throws NotFoundException, IllegalArgumentException {
        if (piece.getId() != id) throw new IllegalArgumentException();

        try (PieceDAO pieceDAO = PieceDAO.of()) {
            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();

            if( pieceDAO.find(id) == null) throw new NotFoundException();

            pieceDAO.persist(piece);

            transaction.commit();
        }
        return piece;
    }

    @GET
    @Path("pieces/{id}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public Piece getPiece(@PathParam("id") long id) throws NotFoundException {
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(id);
            if( piece == null) throw new NotFoundException();
        }
        return piece;
    }



    @GET
    @Path("pieces")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public Map<Long, Piece> getPieces() throws NotFoundException {
        Map<Long, Piece> map;

        try (PieceDAO pieceDAO = PieceDAO.of()) {

            map = pieceDAO.findAll().stream()
                    .collect(Collectors.toMap(Piece::getId, piece -> piece));

        }
        return map ;
    }

    @GET
    @Path("pieces/size")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public int getPieceSize() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            return pieceDAO.findAll().size();

        }
    }

    @DELETE
    @Path("pieces/{id}")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void removePiece(@PathParam("id") long id) throws NotFoundException {
        try (PieceDAO pieceDAO = PieceDAO.of()) {
            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();
            Piece piece = pieceDAO.find(id);
            if( piece == null) throw new NotFoundException();
            pieceDAO.remove(piece);

            transaction.commit();
        }
    }

    @DELETE
    @Path("pieces")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void deletePieces() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {
            pieceDAO.deleteAll();
        }
    }
}
