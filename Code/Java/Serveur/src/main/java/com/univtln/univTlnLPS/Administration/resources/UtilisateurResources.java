package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Administration.model.Superviseur;
import com.univtln.univTlnLPS.Administration.model.Utilisateur;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("utilisateur")
@XmlRootElement
public class UtilisateurResources {

    private static long lastId = 0;

    private static final Utilisateur modelUtilisateur = new Utilisateur();

    final MutableLongObjectMap<Utilisateur> utilisateurs = LongObjectMaps.mutable.empty();

    //@PUT
    //@Path("initUtilisateur")
    public void init() throws IllegalArgumentException {
        Utilisateur.builder().CaracteristiquesMachine("").build();
    }


    // add delete update

    public Utilisateur addUtilisateur(Utilisateur utilisateur) throws IllegalArgumentException {
        if (utilisateur.getId() != 0) throw new IllegalArgumentException();
        utilisateur.setId(++lastId);
        utilisateurs.put(utilisateur.getId(), utilisateur);
        return utilisateur;
    }

    public Utilisateur updateUtilisateur(long id, Utilisateur utilisateur) throws NotFoundException, IllegalArgumentException {
        if (utilisateur.getId() != 0) throw new IllegalArgumentException();
        utilisateur.setId(id);;
        if (!utilisateurs.containsKey(id)) throw new NotFoundException();
        utilisateurs.put(id, utilisateur);
        return utilisateur;
    }

    public void removeUtilisateur(long id) throws NotFoundException {
        if (!utilisateurs.containsKey(id)) throw new NotFoundException();
        utilisateurs.remove(id);
    }

}
