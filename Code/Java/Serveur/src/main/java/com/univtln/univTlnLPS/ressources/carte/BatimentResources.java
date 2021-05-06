package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

import java.util.HashSet;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class BatimentResources {

    private static long lastId = 0;

    public static final MutableLongObjectMap<Batiment> batiments = LongObjectMaps.mutable.empty();


    @PUT
    @Path("batiments/init")
    public void init() throws IllegalArgumentException {
        Batiment.builder().etageList(new HashSet<>()).build();
    }

    // add delete update

    @PUT
    @Path("batiments")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Batiment addBatiment(Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(++lastId);
        batiments.put(batiment.getId(), batiment);
        return batiment;
    }

    @POST
    @Path("batiments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Batiment updateBatiment(@PathParam("id") long id, Batiment batiment) throws NotFoundException, IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(id);
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.put(id, batiment);
        return batiment;
    }

    @DELETE
    @Path("batiments/{id}")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void removeBatiment(@PathParam("id") long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.remove(id);
    }

    @GET
    @Path("batiments/{id}")
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public Batiment getBatiment(@PathParam("id") long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        return batiments.get(id);
    }

    @GET
    @Path("batiments/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public int getBatimentSize() {
        return batiments.size();
    }

    @DELETE
    @Path("batiments")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void deleteBatiments() {
        batiments.clear();
        lastId = 0;
    }

    @GET
    @Path("batiments")
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public MutableLongObjectMap<Batiment> getBatiments() {
        return batiments;
    }

}
