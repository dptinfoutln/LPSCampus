package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.model.carte.Etage;
import com.univtln.univTlnLPS.model.carte.Piece;
import com.univtln.univTlnLPS.security.annotations.BasicAuth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;
import jakarta.ws.rs.*;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class EtageResources {


    private static long lastId = 0;

    public static final MutableLongObjectMap<Etage> etages = LongObjectMaps.mutable.empty();


    @PUT
    @Path("etages/init")
    public void init() throws IllegalArgumentException {
        Etage.builder().build();
    }

    // add delete update

    @PUT
    @Path("etages")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Etage addEtage(Etage etage) throws IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(++lastId);
        etages.put(etage.getId(), etage);
        return etage;
    }

    @POST
    @Path("etages/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public Etage updateEtage(@PathParam("id") long id, Etage etage) throws NotFoundException, IllegalArgumentException {
        if (etage.getId() != 0) throw new IllegalArgumentException();
        etage.setId(id);
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.put(id, etage);
        return etage;
    }

    @DELETE
    @Path("etages/{id}")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void removeEtage(@PathParam("id") long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        etages.remove(id);
    }

    @GET
    @Path("etages/{id}")
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public Etage getEtage(@PathParam("id") long id) throws NotFoundException {
        if (!etages.containsKey(id)) throw new NotFoundException();
        return etages.get(id);
    }

    @GET
    @Path("etages/size")
    @RolesAllowed({"ADMIN", "SUPER"})
    @BasicAuth
    public int getEtageSize() {
        return etages.size();
    }

    @DELETE
    @Path("etages")
    @RolesAllowed({"ADMIN"})
    @BasicAuth
    public void deleteEtages() {
        etages.clear();
        lastId = 0;
    }

    @GET
    @Path("etages")
    @RolesAllowed({"SUPER", "ADMIN"})
    @BasicAuth
    public MutableLongObjectMap<Etage> getEtages() {
        return etages;
    }

}