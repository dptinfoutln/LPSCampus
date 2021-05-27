package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Utilisateur;

import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Utilisateurs
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class UtilisateurDAO extends AbstractDAO<Utilisateur> {

    /**
     * @param caract
     * @return liste d'utilisateurs ayant pour caracteristiques machine caract
     */
       public List<Utilisateur> findByCaract(String caract) {
        return getEntityManager().createNamedQuery("utilisateur.findByCaract")
                .setParameter("caract", caract)
                .getResultList();
    }

    /**
     * @param scan
     * @return liste d'Utilisateurs ayant pour lastScan scan (1 element)
     */
    public List<Utilisateur> findByScan(ScanData scan) {
        return getEntityManager().createNamedQuery("utilisateur.findByScan")
                .setParameter("scan", scan)
                .getResultList();
    }
}