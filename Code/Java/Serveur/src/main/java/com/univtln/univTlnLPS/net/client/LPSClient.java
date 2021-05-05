package com.univtln.univTlnLPS.net.client;

import com.univtln.univTlnLPS.dao.administration.AdministrateurDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.carte.Campus;
import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;

@Log
public class LPSClient {

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");

        String responseInitAsStringInit = webResource.path("LaGarde/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringInit);

        try (AdministrateurDAO adminDAO = AdministrateurDAO.of()){
            EntityTransaction transaction = adminDAO.getTransaction();
            transaction.begin();

            //Campus camp = CampusDAO.of().findByName("tln").get(0);
            Administrateur admin = Administrateur.builder()
                    .email("leviathan@univ-tln.fr")
                    /*.campus(camp)*/.build();
            admin.setPasswordHash("trempette");

            adminDAO.persist(admin);

            transaction.commit();
        }

        /*responseInitAsStringInit = webResource.path("LaGarde/campus")
                .request().put(Entity.entity(new Campus("name222", "",
                        new HashSet<>(), 1, null),
                        MediaType.APPLICATION_JSON), String.class);
        log.info(responseInitAsStringInit);*/
    }
}
