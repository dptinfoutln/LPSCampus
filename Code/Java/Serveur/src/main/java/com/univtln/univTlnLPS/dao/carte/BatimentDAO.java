package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.model.scan.WifiData;
import jakarta.persistence.EntityTransaction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Batiments
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class BatimentDAO extends AbstractDAO<Batiment> {
    /**
     * Renvoie la liste des Pieces ayant pour nom name
     *
     * @param name the name
     * @return list
     */
    public List<Batiment> findByName(String name) {
        return getEntityManager().createNamedQuery("batiment.findByName")
                .setParameter("name", name)
                .getResultList();
    }

    public void safeRemove(Batiment batiment) {
        EtageDAO etageDao = EtageDAO.of();

        EntityTransaction transaction = etageDao.getTransaction();
        transaction.begin();
        for (Etage etage : etageDao.findByBatiment(batiment)) {
            etageDao.safeRemove(etage);
        }
        transaction.commit();

        remove(batiment);
    }

    public List<Batiment> findByCampus(Campus campus) {
        return getEntityManager().createNamedQuery("batiment.findByCampus")
                .setParameter("campus", campus)
                .getResultList();
    }
}