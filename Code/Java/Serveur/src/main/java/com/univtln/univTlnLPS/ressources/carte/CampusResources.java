package com.univtln.univTlnLPS.ressources.carte;

import com.univtln.univTlnLPS.model.carte.Batiment;
import com.univtln.univTlnLPS.model.carte.Campus;

import java.util.HashSet;

import com.univtln.univTlnLPS.model.carte.Piece;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class CampusResources {

    private static final MutableLongObjectMap<Campus> campus = LongObjectMaps.mutable.empty();

    @PUT
    @Path("campus/init")
    public void init() throws IllegalArgumentException {
        campus.put(1, new Campus("plan", new HashSet<>(), 1, null));
    }

    @GET
    @Path("campus")
    public MutableLongObjectMap<Campus> getCampus() {
        return campus;
    }

}
