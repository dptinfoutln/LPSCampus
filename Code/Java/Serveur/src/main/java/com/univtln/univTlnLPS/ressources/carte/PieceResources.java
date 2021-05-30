package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.security.annotations.JWTAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Piece resources.
 */
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class PieceResources {

    /**
     * Init.
     *
     * @throws IllegalArgumentException the illegal argument exception
     */
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

    /**
     * Add piece string.
     *
     * @param piece the piece
     * @return the string
     * @throws IllegalArgumentException the illegal argument exception
     */
    @PUT
    @Path("pieces")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String addPiece(Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();

        if (piece.getEtage() == null) throw new IllegalArgumentException();
        Etage et = EtageDAO.of().find(piece.getEtage().getId());

        if (et == null) throw new IllegalArgumentException();

        try (PieceDAO pieceDAO = PieceDAO.of()) {
            if (!pieceDAO.findByName(piece.getName()).isEmpty())
                return "WARNING: La piece existe déjà";

            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();

            piece.setEtage(et);
            pieceDAO.persist(piece);

            transaction.commit();
        }
        return "success";
    }

    /**
     * Update piece piece.
     *
     * @param id    the id
     * @param piece the piece
     * @return the piece
     * @throws NotFoundException        the not found exception
     * @throws IllegalArgumentException the illegal argument exception
     */
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

    /**
     * Gets piece.
     *
     * @param id the id
     * @return the piece
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Gets piece by name.
     *
     * @param name the name
     * @return the piece by name
     * @throws NotFoundException the not found exception
     */
    @GET
    @Path("pieces/name/{name}")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public Piece getPieceByName(@PathParam("name") String name) throws NotFoundException {
        Piece piece;
        name = name.replace("\n", "").replace(" ", "");
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            piece = pieceDAO.findByName(name).get(0);
            if( piece == null) throw new NotFoundException();
        }
        return piece;
    }

    /**
     * Gets piece size.
     *
     * @return the piece size
     */
    @GET
    @Path("pieces/size")
    @RolesAllowed({"SUPER", "ADMIN"})
    @JWTAuth
    public int getPieceSize() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {

            return pieceDAO.findAll().size();

        }
    }

    /**
     * Gets pieces.
     *
     * @return the pieces
     * @throws NotFoundException the not found exception
     */
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

    /**
     * Remove piece string.
     *
     * @param id the id
     * @return the string
     * @throws NotFoundException the not found exception
     */
    @DELETE
    @Path("pieces/{id}")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String removePiece(@PathParam("id") long id) throws NotFoundException {
        try (PieceDAO pieceDAO = PieceDAO.of()) {
            EntityTransaction transaction = pieceDAO.getTransaction();

            transaction.begin();
            Piece piece = pieceDAO.find(id);
            if( piece == null) throw new NotFoundException();
            pieceDAO.safeRemove(piece);

            transaction.commit();
        }

        return "success";
    }

    /**
     * Delete pieces string.
     *
     * @return the string
     */
    @DELETE
    @Path("pieces")
    @RolesAllowed({"ADMIN"})
    @JWTAuth
    public String deletePieces() {
        try (PieceDAO pieceDAO = PieceDAO.of()) {
            pieceDAO.deleteAll();
        }

        return "success";
    }
}
