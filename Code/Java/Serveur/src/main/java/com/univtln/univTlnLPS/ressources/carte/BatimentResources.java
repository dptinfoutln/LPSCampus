package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.model.carte.Batiment;
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

    final MutableLongObjectMap<Batiment> batiments = LongObjectMaps.mutable.empty();


    @PUT
    @Path("batiments/init")
    public void init() throws IllegalArgumentException {
        Batiment.builder().etageList(new HashSet<>()).build();
    }

    // add delete update

    @PUT
    @Path("batiments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Batiment addBatiment(Batiment batiment) throws IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(++lastId);
        batiments.put(batiment.getId(), batiment);
        return batiment;
    }

    @POST
    @Path("batiments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Batiment updateBatiment(@PathParam("id") long id, Batiment batiment) throws NotFoundException, IllegalArgumentException {
        if (batiment.getId() != 0) throw new IllegalArgumentException();
        batiment.setId(id);
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.put(id, batiment);
        return batiment;
    }

    @DELETE
    @Path("batiments/{id}")
    public void removeBatiment(@PathParam("id") long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        batiments.remove(id);
    }

    @GET
    @Path("batiments/{id}")
    public Batiment getBatiment(@PathParam("id") long id) throws NotFoundException {
        if (!batiments.containsKey(id)) throw new NotFoundException();
        return batiments.get(id);
    }

    @GET
    @Path("batiments/size")
    public int getBatimentSize() {
        return batiments.size();
    }

    @DELETE
    @Path("batiments")
    public void deleteBatiments() {
        batiments.clear();
        lastId = 0;
    }

}
