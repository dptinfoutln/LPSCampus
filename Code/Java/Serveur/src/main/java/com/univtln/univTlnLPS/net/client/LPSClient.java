package com.univtln.univTlnLPS.net.client;

import com.univtln.univTlnLPS.common.LPSModele;
import com.univtln.univTlnLPS.dao.administration.AdministrateurDAO;
import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.model.scan.WifiData;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Log
public class LPSClient {

    public static String auth(String login, String pass){
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");

        String basic = "Basic" + Base64.getEncoder().encodeToString((login + ":" + pass).getBytes());
        String responseInitAsStringInit = webResource.path("LaGarde/connexion")
                .request().header("Authorization", basic)
                .post(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringInit);

        return responseInitAsStringInit;
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");

        //String token = auth("leviathan@univ-tln.fr", "trempette");

        /*Campus camp = Campus.builder().name("LaGardee").plan("").id(0).build();
        String responseInitAsStringInit = webResource.path("LaGarde/campus")
                .request().header("Authorization", "Bearer" + token)
                .put(Entity.entity(camp,
                        MediaType.APPLICATION_JSON), String.class);
        log.info(responseInitAsStringInit);*/

        /*String responseInitAsStringInit = webResource.path("LaGarde/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringInit);*/

        LPSModele.init();

        /*responseInitAsStringInit = webResource.path("LaGarde/campus")
                .request().put(Entity.entity(new Campus("name222", "",
                        new HashSet<>(), 1, null),
                        MediaType.APPLICATION_JSON), String.class);
        log.info(responseInitAsStringInit);*/
    }
}
