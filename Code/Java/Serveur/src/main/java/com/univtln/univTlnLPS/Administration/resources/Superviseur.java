package com.univtln.univTlnLPS.Administration.resources;

import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("superviseur")
@XmlRootElement
public class Superviseur {
    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Administration.model.Superviseur modelSuperviseur =
            com.univtln.univTlnLPS.Administration.model.Superviseur.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Administration.model.Superviseur> superviseurs =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initSuperviseur")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Administration.model.Superviseur superviseur =
                new com.univtln.univTlnLPS.Administration.model.Superviseur();
        superviseur.builder().loginHash("").passwordHash("").build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Administration.model.Superviseur addSuperviseur(com.univtln.univTlnLPS.Administration.model.Superviseur superviseur) throws IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(++lastId);
        superviseurs.put(superviseur.getId(), superviseur);
        return superviseur;
    }

    public com.univtln.univTlnLPS.Administration.model.Superviseur updateSuperviseur(long id, com.univtln.univTlnLPS.Administration.model.Superviseur superviseur) throws NotFoundException, IllegalArgumentException {
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
