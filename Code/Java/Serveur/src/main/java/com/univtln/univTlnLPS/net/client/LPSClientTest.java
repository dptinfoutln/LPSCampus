package com.univtln.univTlnLPS.net.client;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.administration.UtilisateurDAO;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Log
public class LPSClientTest {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget webResource = client.target("http://localhost:9998/LPS");

        String responseInitAsStringInit = webResource.path("LaGarde/init")
                .request().put(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
        log.info(responseInitAsStringInit);

        test();
    }

    public static void test(){
        // Test interactions BDD

        Utilisateur user1, user2;
        Superviseur super1;
        List<Utilisateur> utilisateurList = Arrays.asList(
                user1 = Utilisateur.builder().build(),
                user2 = Utilisateur.builder().build(),
                super1 = Superviseur.builder().email("truc").build());

        try (UtilisateurDAO utilisateurDAO = UtilisateurDAO.of()) {

            EntityTransaction transaction = utilisateurDAO.getTransaction();

            transaction.begin();
            utilisateurDAO.persist(user1);
            utilisateurDAO.persist(user2);

            System.out.println("User 1:" + utilisateurDAO.find(1));
            System.out.println("User 2:" + utilisateurDAO.find(2));

            System.out.println(utilisateurDAO.findAll());

            transaction.commit();

        }

        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            EntityTransaction transaction = superviseurDAO.getTransaction();

            transaction.begin();
            superviseurDAO.persist(super1);

            System.out.println("super 3:" + superviseurDAO.find(3));
            System.out.println(superviseurDAO.findAll());

            transaction.commit();
        }

        try (UtilisateurDAO utilisateurDAO = UtilisateurDAO.of()) {
            System.out.println(utilisateurDAO.findAll());
        }
    }
}
