package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Piece;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Pieces
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class PieceDAO extends AbstractDAO<Piece> {
    /**
     * Renvoie la liste des Pieces ayant pour nom name
     *
     * @param name the name
     * @return list
     */
    public List<Piece> findByName(String name) {
        return getEntityManager().createNamedQuery("piece.findByName")
                .setParameter("name", name)
                .getResultList();
    }
}