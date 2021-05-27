package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Campus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Campus
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class CampusDAO extends AbstractDAO<Campus> {
    /**
     * Renvoie la liste des Pieces ayant pour nom name
     *
     * @param name the name
     * @return list
     */
    public List<Campus> findByName(String name) {
        return getEntityManager().createNamedQuery("campus.findByName")
                .setParameter("name", name)
                .getResultList();
    }
}