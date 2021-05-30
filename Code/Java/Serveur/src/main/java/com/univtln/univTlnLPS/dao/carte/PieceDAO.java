package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.dao.scan.ScanDataDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.EntityTransaction;
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

    public void safeRemove(Piece piece) {
        ScanDataDAO scanDataDao = ScanDataDAO.of();

        EntityTransaction transaction = scanDataDao.getTransaction();
        transaction.begin();
        for (ScanData scanData : scanDataDao.findByPiece(piece)) {
            scanDataDao.safeRemove(scanData);
        }
        transaction.commit();

        remove(piece);
    }

    public List<Piece> findByEtage(Etage etage) {
        return getEntityManager().createNamedQuery("piece.findByEtage")
                .setParameter("etage", etage)
                .getResultList();
    }
}