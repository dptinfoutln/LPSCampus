package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

/**
 * DAO des Formulaires de demande a devenir Superviseur
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class FormDevenirSuperDAO extends AbstractDAO<FormDevenirSuper> {
    /**
     * Find by email list.
     *
     * @param email the email
     * @return liste des Formulaires correspondant a email
     */
    public List<FormDevenirSuper> findByEmail(String email) {
        return getEntityManager().createNamedQuery("form.findByEmail")
                .setParameter("email", email)
                .getResultList();
    }
}
