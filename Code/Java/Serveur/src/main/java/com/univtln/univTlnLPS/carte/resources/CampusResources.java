package com.univtln.univTlnLPS.carte.resources;

import com.univtln.univTlnLPS.carte.model.Campus;

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
