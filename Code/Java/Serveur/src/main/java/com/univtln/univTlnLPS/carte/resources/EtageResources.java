package com.univtln.univTlnLPS.carte.resources;

import com.univtln.univTlnLPS.carte.model.Etage;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class EtageResources {


    private static long lastId = 0;

    final MutableLongObjectMap<Etage> etages = LongObjectMaps.mutable.empty();


    @PUT
    @Path("etages/init")
    public void init() throws IllegalArgumentException {
        Etage.builder().build();
    }

    // add delete update

    @PUT
    @Path("etages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Etage addEtage(Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(++lastId);
        etages.put(etage.getId(), etage);
        return etage;
    }

    @POST
    @Path("etages/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Etage updateEtage(@PathParam("id") long id, Etage etage) throws NotFoundException, IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(id);;
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.put(id, etage);
        return etage;
    }

    @DELETE
    @Path("etages/{id}")
    public void removeEtage(@PathParam("id") long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.remove(id);
    }

    @GET
    @Path("etages/{id}")
    public Etage getEtage(@PathParam("id") long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        return etages.get(id);
    }

    @GET
    @Path("etages/size")
    public int getEtageSize() {
        return etages.size();
    }

    @DELETE
    @Path("etages")
    public void deleteBatiments() {
        etages.clear();
        lastId = 0;
    }

}