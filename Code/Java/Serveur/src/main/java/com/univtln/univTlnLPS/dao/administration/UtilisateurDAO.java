package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Utilisateur;

import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class UtilisateurDAO extends AbstractDAO<Utilisateur> {
    public List<Utilisateur> findById(long id) {
        return getEntityManager().createNamedQuery("utilisateur.findByID")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Utilisateur> findByCaract(String caract) {
        return getEntityManager().createNamedQuery("utilisateur.findByCaract")
                .setParameter("caract", caract)
                .getResultList();
    }

    public List<Utilisateur> findByScanId(long idScan) {
        return getEntityManager().createNamedQuery("utilisateur.findByScanId")
                .setParameter("idScan", idScan)
                .getResultList();
    }

    public void update(String caract, ScanData scan) {
        // On supprime le precedent scan de l'utilisateur de la BDD
        // /!\ TO DO /!\ (waiting for scanDao being done)

        // Puis on update
        getEntityManager().createNamedQuery("utilisateur.update")
                .setParameter("scan", scan);
        return;
    }

    public void delete(long id) {
        getEntityManager().createNamedQuery("utilisateur.delete")
                .setParameter("id", id);
        return;
    }
}