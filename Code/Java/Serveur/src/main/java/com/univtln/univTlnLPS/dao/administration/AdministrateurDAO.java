package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Campus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Administrateurs
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class AdministrateurDAO extends AbstractDAO<Administrateur> {

    /**
     * Find by campus list.
     *
     * @param campus the campus
     * @return liste des superviseurs ayant pour Campus campus
     */
    public List<Superviseur> findByCampus(Campus campus) {
        return getEntityManager().createNamedQuery("administrateur.findByCampus")
                .setParameter("campus", campus)
                .getResultList();
    }
}