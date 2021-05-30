package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import jakarta.persistence.EntityTransaction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Etages
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class EtageDAO extends AbstractDAO<Etage> {
    /**
     * Renvoie la liste Etages ayant pour nom name (1 etage)
     *
     * @param name the name
     * @return list
     */
    public List<Etage> findByName(String name) {
        return getEntityManager().createNamedQuery("etage.findByName")
                .setParameter("name", name)
                .getResultList();
    }

    public void safeRemove(Etage etage) {
        PieceDAO pieceDAO = PieceDAO.of();

        EntityTransaction transaction = pieceDAO.getTransaction();
        transaction.begin();
        for (Piece piece : pieceDAO.findByEtage(etage)) {
            pieceDAO.safeRemove(piece);
        }
        transaction.commit();

        remove(etage);
    }

    public List<Etage> findByBatiment(Batiment batiment) {
        return getEntityManager().createNamedQuery("etage.findByBatiment")
                .setParameter("batiment", batiment)
                .getResultList();
    }
}