package com.univtln.univTlnLPS.dao.administration;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.FormDevenirSuper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class FormDevenirSuperDAO extends AbstractDAO<FormDevenirSuper> {
    public List<FormDevenirSuper> findByEmail(String email) {
        return getEntityManager().createNamedQuery("form.findByEmail")
                .setParameter("email", email)
                .getResultList();
    }
}
