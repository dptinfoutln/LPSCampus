package com.univtln.univTlnLPS.ressources.administration;

import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.carte.Campus;
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
