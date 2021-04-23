package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.List;

public class Etage {


    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Carte.model.Etage modelEtage =
            com.univtln.univTlnLPS.Carte.model.Etage.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Carte.model.Etage> etages =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initEtage")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Carte.model.Etage etage =
                com.univtln.univTlnLPS.Carte.model.Etage.of();
        etage.builder().build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Carte.model.Etage addEtage(com.univtln.univTlnLPS.Carte.model.Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(++lastId);
        etages.put(etage.getId(), etage);
        return etage;
    }

    public com.univtln.univTlnLPS.Carte.model.Etage updateEtage(long id, com.univtln.univTlnLPS.Carte.model.Etage etage) throws NotFoundException, IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(id);;
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.put(id, etage);
        return etage;
    }

    public void removeEtage(long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.remove(id);
    }

    public com.univtln.univTlnLPS.Carte.model.Etage getEtage(long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        return etages.get(id);
    }

    public int getEtageSize() {
        return etages.size();
    }

    public void deleteBatiments() {
        etages.clear();
        lastId = 0;
    }

}