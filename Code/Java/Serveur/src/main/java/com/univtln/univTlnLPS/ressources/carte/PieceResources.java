package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class PieceResources {

    public static void init() throws IllegalArgumentException {
        long i;

        try (EtageDAO etDAO = EtageDAO.of()) {
            EntityTransaction transactionEt = etDAO.getTransaction();
            transactionEt.begin();
            List<Etage> l = etDAO.findByName("U-rdc");
            if ( !l.isEmpty() ) {
                Etage et = l.get(0);

                try (PieceDAO pDAO = PieceDAO.of()) {
                    EntityTransaction transaction = pDAO.getTransaction();
                    transaction.begin();

                    for(i = 0; i < 5; i++){
                        Piece p = Piece.builder().build();

                        p.setEtage(et);
                        et.getPieceList().add(p);
                        p.setPosition_x((int)i);
                        p.setPosition_y((int)i);
                        p.setScanList(new HashSet<>());
                        p.setName("U-00"+(i+1));

                        pDAO.persist(p);
                    }

                    transaction.commit();
                }

            }

            etDAO.persist(l.get(0));

            transactionEt.commit();
        }
    }

    @PUT
    @Path("pieces")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public Piece addPiece(Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();

        if (piece.getEtage() == null) throw new IllegalArgumentException();
        Etage et = EtageDAO.of().find(piece.getEtage().getId());

        if (et == null) throw new IllegalArgumentException();

        try (PieceDAO pieceDAO = PieceDAO.of()) {
            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();

            piece.setEtage(et);
            pieceDAO.persist(piece);

            transaction.commit();
        }
        return piece;
    }

    @POST
    @Path("pieces/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
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
    @JWTAuth
    public Piece getPiece(@PathParam("id") long id) throws NotFoundException {
        Piece piece;
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.find(id);
            if( piece == null) throw new NotFoundException();
        }
        return piece;
    }

    @GET
    @Path("pieces/size")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public int getPieceSize() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            return pieceDAO.findAll().size();

        }
    }

    @GET
    @Path("pieces")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public Map<Long, Piece> getPieces() throws NotFoundException {
        Map<Long, Piece> map;

        try (PieceDAO pieceDAO = PieceDAO.of()) {

            map = pieceDAO.findAll().stream()
                    .collect(Collectors.toMap(Piece::getId, piece -> piece));

        }
        return map ;
    }

    @DELETE
    @Path("pieces/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
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
    @JWTAuth
    public void deletePieces() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {
            pieceDAO.deleteAll();
        }
    }
}
