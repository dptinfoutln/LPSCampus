package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import com.univtln.univTlnLPS.Carte.model.Etage;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.List;

public class EtageResources {


    private static long lastId = 0;

    private static final Etage modelEtage = new Etage();

    final MutableLongObjectMap<Etage> etages = LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initEtage")
    public void init() throws IllegalArgumentException {
        Etage.builder().build();
    }

    // add delete update

    public Etage addEtage(Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(++lastId);
        etages.put(etage.getId(), etage);
        return etage;
    }

    public Etage updateEtage(long id, Etage etage) throws NotFoundException, IllegalArgumentException {
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

    public Etage getEtage(long id) throws NotFoundException {
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