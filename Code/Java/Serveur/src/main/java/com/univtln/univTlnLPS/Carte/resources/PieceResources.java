package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Piece;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

public class PieceResources {


    private static long lastId = 0;

    private static final Piece modelPiece = new Piece();

    final MutableLongObjectMap<Piece> pieces = LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initPiece")
    public void init() throws IllegalArgumentException {
        Piece.builder().build();
    }

    // add delete update

    public Piece addPiece(com.univtln.univTlnLPS.Carte.model.Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();
        piece.setId(++lastId);
        pieces.put(piece.getId(), piece);
        return piece;
    }

    public Piece updatePiece(long id, Piece piece) throws NotFoundException, IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();
        piece.setId(id);;
        if (!pieces.containsKey(id)) throw new NotFoundException();
        pieces.put(id, piece);
        return piece;
    }

    public void removePiece(long id) throws NotFoundException {
        if (!pieces.containsKey(id)) throw new NotFoundException();
        pieces.remove(id);
    }

    public Piece getPiece(long id) throws NotFoundException {
        if (!pieces.containsKey(id)) throw new NotFoundException();
        return pieces.get(id);
    }

    public int getPieceSize() {
        return pieces.size();
    }

    public void deletePieces() {
        pieces.clear();
        lastId = 0;
    }
}
