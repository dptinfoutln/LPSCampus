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

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class ScanDataDAO extends AbstractDAO<ScanData> {
    public List<ScanData> findByPiece(Piece piece) {
        return getEntityManager().createNamedQuery("scanData.findByPiece")
                .setParameter("piece", piece)
                .getResultList();
    }
    public List<ScanData> findByUser(Utilisateur user) {
        return getEntityManager().createNamedQuery("scanData.findByUser")
                .setParameter("user", user)
                .getResultList();
    }
    public List<ScanData> findBySuper(Superviseur superviseur) {
        return getEntityManager().createNamedQuery("scanData.findBySuper")
                .setParameter("superviseur", superviseur)
                .getResultList();
    }
    public List<ScanData> findBySuperAndPiece(Superviseur superviseur, Piece piece) {
        return getEntityManager().createNamedQuery("scanData.findBySuperAndPiece")
                .setParameter("superviseur", superviseur)
                .setParameter("piece", piece)
                .getResultList();
    }

    public List<Piece> findScanPiecesBySuper(Superviseur superviseur) {
        return getEntityManager().createNamedQuery("scanData.findScanPiecesBySuper")
                .setParameter("superviseur", superviseur)
                .getResultList();
    }
}