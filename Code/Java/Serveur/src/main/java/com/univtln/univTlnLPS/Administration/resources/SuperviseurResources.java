package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Administration.model.Superviseur;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class SuperviseurResources {
    private static long lastId = 0;

    final MutableLongObjectMap<Superviseur> superviseurs = LongObjectMaps.mutable.empty();


    @PUT
    @Path("superviseurs/init")
    public void init() throws IllegalArgumentException {
        Superviseur.builder().loginHash("").passwordHash("").build();
    }

    // add delete update

    @PUT
    @Path("superviseurs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Superviseur addSuperviseur(Superviseur superviseur) throws IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(++lastId);
        superviseurs.put(superviseur.getId(), superviseur);
        return superviseur;
    }

    @POST
    @Path("superviseurs/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Superviseur updateSuperviseur(@PathParam("id") long id, Superviseur superviseur) throws NotFoundException, IllegalArgumentException {
        if (superviseur.getId() != 0) throw new IllegalArgumentException();
        superviseur.setId(id);;
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.put(id, superviseur);
        return superviseur;
    }

    @DELETE
    @Path("superviseurs/{id}")
    public void removeSuperviseur(@PathParam("id") long id) throws NotFoundException {
        if (!superviseurs.containsKey(id)) throw new NotFoundException();
        superviseurs.remove(id);
    }


}
