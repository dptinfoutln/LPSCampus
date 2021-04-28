package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Campus;
import java.util.ArrayList;
import java.util.HashSet;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class CampusResources {

    @PUT
    @Path("campus/init")
    public void init() throws IllegalArgumentException {
        Campus.builder().batimentList(new HashSet<>()).build();
    }

}
