package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Administration.model.Superviseur;
import com.univtln.univTlnLPS.Carte.model.Etage;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.ArrayList;
import java.util.List;

public class Batiment {

    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Carte.model.Batiment modelBatiment =
            com.univtln.univTlnLPS.Carte.model.Batiment.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Carte.model.Batiment> batiments =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initBatiment")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Carte.model.Batiment batiment =
                com.univtln.univTlnLPS.Carte.model.Batiment.of();
        batiment.builder().etageList(new ArrayList<>()).build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Carte.model.Batiment addBatiment(com.univtln.univTlnLPS.Carte.model.Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(++lastId);
        batiments.put(batiment.getId(), batiment);
        return batiment;
    }

    public com.univtln.univTlnLPS.Carte.model.Batiment updateBatiment(long id, com.univtln.univTlnLPS.Carte.model.Batiment batiment) throws NotFoundException, IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(id);;
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.put(id, batiment);
        return batiment;
    }

    public void removeBatiment(long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.remove(id);
    }

    public com.univtln.univTlnLPS.Carte.model.Batiment getBatiment(long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        return batiments.get(id);
    }

    public int getBatimentSize() {
        return batiments.size();
    }

    public void deleteBatiments() {
        batiments.clear();
        lastId = 0;
    }

}
