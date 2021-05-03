package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Utilisateur;

import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class UtilisateurDAO extends AbstractDAO<Utilisateur> {

       public List<Utilisateur> findByCaract(String caract) {
        return getEntityManager().createNamedQuery("utilisateur.findByCaract")
                .setParameter("caract", caract)
                .getResultList();
    }

    public List<Utilisateur> findByScan(ScanData scan) {
        return getEntityManager().createNamedQuery("utilisateur.findByScan")
                .setParameter("scan", scan)
                .getResultList();
    }
}