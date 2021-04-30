package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class AdministrateurDAO extends AbstractDAO<Administrateur> {
    public List<Administrateur> findById(long id) {
        return getEntityManager().createNamedQuery("administrateur.findByID")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Administrateur> update(Campus campus,
                                       String email,
                                       String pWH,
                                       String caract,
                                       ScanData scan) {
        return getEntityManager().createNamedQuery("superviseur.update")
                .setParameter("campus", campus)
                .setParameter("email", email)
                .setParameter("pWH", pWH)
                .setParameter("caract", caract)
                .setParameter("scan", scan)
                .getResultList();
    }

    public void delete(long id) {
        getEntityManager().createNamedQuery("administrateur.delete")
                .setParameter("id", id);
        return;
    }
}