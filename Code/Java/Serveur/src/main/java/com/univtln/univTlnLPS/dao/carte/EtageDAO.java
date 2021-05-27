package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Etage;
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
     * @param name
     * @return
     */
    public List<Etage> findByName(String name) {
        return getEntityManager().createNamedQuery("etage.findByName")
                .setParameter("name", name)
                .getResultList();
    }
}