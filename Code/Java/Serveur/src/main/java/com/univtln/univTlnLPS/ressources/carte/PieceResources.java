package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;


@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class PieceResources {
    private static long lastId = 0;

    final MutableLongObjectMap<Piece> pieces = LongObjectMaps.mutable.empty();

    @PUT
    @Path("pieces/init")
    public void init() throws IllegalArgumentException {
        long i;
        for(i = 0; i < 5; i++){
            Piece p = Piece.builder().build();
            p.setId(i);
            p.setPosition_x((int)i);
            pieces.put(i, p);
        }
        lastId = 5;
    }

    // add delete update

    @PUT
    @Path("pieces")
    @Consumes(MediaType.APPLICATION_JSON)
    public Piece addPiece(Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();
        piece.setId(++lastId);
        pieces.put(piece.getId(), piece);
        return piece;
    }

    @POST
    @Path("pieces/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Piece updatePiece(@PathParam("id") long id, Piece piece) throws NotFoundException, IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();
        piece.setId(id);
        if (!pieces.containsKey(id)) throw new NotFoundException();
        pieces.put(id, piece);
        return piece;
    }

    @DELETE
    @Path("pieces/{id}")
    public void removePiece(@PathParam("id") long id) throws NotFoundException {
        if (!pieces.containsKey(id)) throw new NotFoundException();
        pieces.remove(id);
    }

    @GET
    @Path("pieces/{id}")
    public Piece getPiece(@PathParam("id") long id) throws NotFoundException {
        if (!pieces.containsKey(id)) throw new NotFoundException();
        return pieces.get(id);
    }

    @GET
    @Path("pieces")
    public MutableLongObjectMap<Piece> getPieces() throws NotFoundException {
        for(long i = 0; i < 5; i++){
            Piece p = Piece.builder().build();
            p.setId(i);
            p.setPosition_x((int)i);
            pieces.put(i, p);
        }
        return pieces;
    }

    @GET
    @Path("pieces/size")
    public int getPieceSize() {
        return pieces.size();
    }

    @DELETE
    @Path("pieces")
    public void deletePieces() {
        pieces.clear();
        lastId = 0;
    }
}
