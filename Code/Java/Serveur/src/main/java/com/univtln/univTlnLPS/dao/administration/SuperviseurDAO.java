package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.model.scan.ScanData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class SuperviseurDAO extends AbstractDAO<Superviseur> {

    public List<Superviseur> findByEmail(String email) {
        return getEntityManager().createNamedQuery("superviseur.findByEmail")
                .setParameter("email", email)
                .getResultList();
    }

    public List<Superviseur> findByAccount(String email, String passwordHash) {
        return getEntityManager().createNamedQuery("superviseur.findByAccount")
                .setParameter("email", email)
                .setParameter("passwordHash", passwordHash)
                .getResultList();
    }


}