package com.univtln.univTlnLPS.Carte.resources;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

public class Piece {


    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Carte.model.Piece modelPiece =
            com.univtln.univTlnLPS.Carte.model.Piece.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Carte.model.Piece> pieces =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initPiece")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Carte.model.Piece piece =
                com.univtln.univTlnLPS.Carte.model.Piece.of();
        piece.builder().build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Carte.model.Piece addPiece(com.univtln.univTlnLPS.Carte.model.Piece piece) throws IllegalArgumentException {
        if (piece.getId() != 0) throw new IllegalArgumentException();
        piece.setId(++lastId);
        pieces.put(piece.getId(), piece);
        return piece;
    }

    public com.univtln.univTlnLPS.Carte.model.Piece updatePiece(long id, com.univtln.univTlnLPS.Carte.model.Piece piece) throws NotFoundException, IllegalArgumentException {
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

    public com.univtln.univTlnLPS.Carte.model.Piece getPiece(long id) throws NotFoundException {
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
