package com.univtln.univTlnLPS.dao.carte;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class EtageDAO extends AbstractDAO<Etage> {
    public List<Etage> findByName(String name) {
        return getEntityManager().createNamedQuery("etage.findByName")
                .setParameter("name", name)
                .getResultList();
    }
}