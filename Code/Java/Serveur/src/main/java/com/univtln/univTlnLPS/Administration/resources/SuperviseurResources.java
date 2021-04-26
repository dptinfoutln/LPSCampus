package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Administration.model.Superviseur;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("superviseur")
@XmlRootElement
public class SuperviseurResources {
    private static long lastId = 0;

    private static final Superviseur modelSuperviseur = new Superviseur();

    final MutableLongObjectMap<Superviseur> superviseurs = LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initSuperviseur")
    public void init() throws IllegalArgumentException {
        Superviseur.builder().loginHash("").passwordHash("").build();
    }

    // add delete update

    public Superviseur addSuperviseur(Superviseur superviseur) throws IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(++lastId);
        superviseurs.put(superviseur.getId(), superviseur);
        return superviseur;
    }

    public Superviseur updateSuperviseur(long id, Superviseur superviseur) throws NotFoundException, IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(id);;
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.put(id, superviseur);
        return superviseur;
    }

    public void removeSuperviseur(long id) throws NotFoundException {
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.remove(id);
    }


}
