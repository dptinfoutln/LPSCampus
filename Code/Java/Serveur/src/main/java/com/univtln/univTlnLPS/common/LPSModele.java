package com.univtln.univTlnLPS.common;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

@Log
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
@Path("LaGarde")
public class LPSModele {

    @PUT
    @Path("init")
    public void init() throws IllegalArgumentException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");

        String responseInitAsStringAdmin = webResource.path("LaGarde/admin/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringAdmin);

        String responseInitAsStringSuper = webResource.path("LaGarde/superviseurs/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringSuper);

        String responseInitAsStringUser = webResource.path("LaGarde/utilisateurs/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringUser);

        String responseInitAsStringBatiment = webResource.path("LaGarde/batiments/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringBatiment);

        String responseInitAsStringCampus = webResource.path("LaGarde/campus/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringCampus);

        String responseInitAsStringEtage = webResource.path("LaGarde/etages/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringEtage);

        String responseInitAsStringPiece = webResource.path("LaGarde/pieces/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringPiece);

        String responseInitAsStringScan = webResource.path("LaGarde/scans/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringScan);

        String responseInitAsStringWifi = webResource.path("LaGarde/wifis/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringWifi);

    }
}
