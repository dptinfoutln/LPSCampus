package com.univtln.univTlnLPS.administration.resources;

import com.univtln.univTlnLPS.administration.model.Administrateur;
import com.univtln.univTlnLPS.carte.model.Campus;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class AdministrateurResources {

    @PUT
    @Path("admin/init")
    public void init() throws IllegalArgumentException {
        Administrateur.builder().campus(new Campus()).email("").passwordHash("").build();
    }

}
