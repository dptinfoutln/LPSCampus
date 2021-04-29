package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.model.administration.Utilisateur;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;


@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class UtilisateurResources {

    private static long lastId = 0;

    final MutableLongObjectMap<Utilisateur> utilisateurs = LongObjectMaps.mutable.empty();

    @PUT
    @Path("utilisateurs/init")
    public void init() throws IllegalArgumentException {
        Utilisateur.builder().CaracteristiquesMachine("").build();
    }


    // add delete update

    @PUT
    @Path("utilisateurs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Utilisateur addUtilisateur(Utilisateur utilisateur) throws IllegalArgumentException {
        if (utilisateur.getId() != 0) throw new IllegalArgumentException();
        utilisateur.setId(++lastId);
        utilisateurs.put(utilisateur.getId(), utilisateur);
        return utilisateur;
    }

    @POST
    @Path("utilisateurs/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Utilisateur updateUtilisateur(@PathParam("id") long id, Utilisateur utilisateur) throws NotFoundException, IllegalArgumentException {
        if (utilisateur.getId() != 0) throw new IllegalArgumentException();
        utilisateur.setId(id);
        if (!utilisateurs.containsKey(id)) throw new NotFoundException();
        utilisateurs.put(id, utilisateur);
        return utilisateur;
    }

    @DELETE
    @Path("utilisateurs/{id}")
    public void removeUtilisateur(@PathParam("id") long id) throws NotFoundException {
        if (!utilisateurs.containsKey(id)) throw new NotFoundException();
        utilisateurs.remove(id);
    }

}
