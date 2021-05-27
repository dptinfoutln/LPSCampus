package com.univtln.univTlnLPS.dao.scan;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Donnees de Scan
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class ScanDataDAO extends AbstractDAO<ScanData> {
    /**
     * Renvoie la liste des scan pour une piece
     *
     * @param piece the piece
     * @return list
     */
    public List<ScanData> findByPiece(Piece piece) {
        return getEntityManager().createNamedQuery("scanData.findByPiece")
                .setParameter("piece", piece)
                .getResultList();
    }

    /**
     * Renvoie la liste des scan de l'Utilisateur user
     *
     * @param user the user
     * @return list
     */
    public List<ScanData> findByUser(Utilisateur user) {
        return getEntityManager().createNamedQuery("scanData.findByUser")
                .setParameter("user", user)
                .getResultList();
    }

    /**
     * Renvoie la liste des scan du superviseur
     * @param superviseur
     * @return
     */
    public List<ScanData> findBySuper(Superviseur superviseur) {
        return getEntityManager().createNamedQuery("scanData.findBySuper")
                .setParameter("superviseur", superviseur)
                .getResultList();
    }

    /**
     * Renvoie la liste des scan du superviseur pour la piece
     * @param superviseur
     * @param piece
     * @return
     */
    public List<ScanData> findBySuperAndPiece(Superviseur superviseur, Piece piece) {
        return getEntityManager().createNamedQuery("scanData.findBySuperAndPiece")
                .setParameter("superviseur", superviseur)
                .setParameter("piece", piece)
                .getResultList();
    }

    /**
     * Renvoie la liste des pieces pour lesquelles
     * le Superviveur superviseur a ajoute des scans
     * @param superviseur
     * @return
     */
    public List<Piece> findScanPiecesBySuper(Superviseur superviseur) {
        return getEntityManager().createNamedQuery("scanData.findScanPiecesBySuper")
                .setParameter("superviseur", superviseur)
                .getResultList();
    }
}