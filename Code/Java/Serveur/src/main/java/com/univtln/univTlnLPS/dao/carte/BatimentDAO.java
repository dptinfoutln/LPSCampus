package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class BatimentDAO extends AbstractDAO<Batiment> {
    public List<Batiment> findByName(String name) {
        return getEntityManager().createNamedQuery("batiment.findByName")
                .setParameter("name", name)
                .getResultList();
    }
}