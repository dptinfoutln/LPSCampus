package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.ArrayList;

public class BatimentResources {

    private static long lastId = 0;

    private static final Batiment modelBatiment = new Batiment();

    final MutableLongObjectMap<Batiment> batiments = LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initBatiment")
    public void init() throws IllegalArgumentException {
        Batiment.builder().etageList(new ArrayList<>()).build();
    }

    // add delete update

    public Batiment addBatiment(Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(++lastId);
        batiments.put(batiment.getId(), batiment);
        return batiment;
    }

    public Batiment updateBatiment(long id, Batiment batiment) throws NotFoundException, IllegalArgumentException {
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
