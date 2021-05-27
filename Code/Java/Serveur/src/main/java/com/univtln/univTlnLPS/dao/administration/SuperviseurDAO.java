package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Superviseurs
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class SuperviseurDAO extends AbstractDAO<Superviseur> {

    /**
     * @param email
     * @return liste de Superviseurs ayant pour email email
     */
    public List<Superviseur> findByEmail(String email) {
        return getEntityManager().createNamedQuery("superviseur.findByEmail")
                .setParameter("email", email)
                .getResultList();
    }

    /**
     * @param email
     * @param passwordHash
     * @return liste des Superviseurs (1 element) correspondant a email et passwordHash
     */
    public List<Superviseur> findByAccount(String email, String passwordHash) {
        return getEntityManager().createNamedQuery("superviseur.findByAccount")
                .setParameter("email", email)
                .setParameter("passwordHash", passwordHash)
                .getResultList();
    }

}