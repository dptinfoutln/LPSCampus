package com.univtln.univTlnLPS.security;

import com.univtln.univTlnLPS.dao.administration.SuperviseurDAO;
import com.univtln.univTlnLPS.dao.administration.UtilisateurDAO;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.security.filter.request.BasicAuthenticationFilter;
import com.univtln.univTlnLPS.security.filter.request.JsonWebTokenFilter;

import jakarta.persistence.EntityTransaction;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.security.Principal;
/**
 * This class define a specific security context after an authentication with either the basic or the JWT filters.
 *
 * @see BasicAuthenticationFilter
 * @see JsonWebTokenFilter
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "newInstance")
public class MySecurityContext implements SecurityContext {
    private final String authenticationScheme;
    private final String userEmail;


    //the authenticated user
    @Override
    public Principal getUserPrincipal() {

        Superviseur superviseur = null;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

            superviseur = superviseurDAO.findByEmail(userEmail).get(0);
            System.out.println(superviseur);

        }
        return superviseur;

    }


    //A method to check if a user belongs to a role
    @Override
    public boolean isUserInRole(String role) {

        // Tout le monde a les autorisations utilisateurs (guest)
        if (role.equals("user"))
            return true;

        // Si on veut verifier des droits d'acces pour une perdonne connectee
        else {
            // On recupere le superviseur correspondant a l'email
            Superviseur superviseur = null;
            try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {

                superviseur = superviseurDAO.findByEmail(userEmail).get(0);
            }

            // Si le role cherche est superviseur et S'il a ete trouve on retourne true
            if (role == "super" && superviseur != null)
                return true;

            // Si le role cherche est administrateur
            if (role == "admin") {
                System.out.println(superviseur);
                if (superviseur instanceof Administrateur);
                    return false;
            }
        }

        return false;
    }

    //Say the access has been secured
    @Override
    public boolean isSecure() {
        return true;
    }

    //The authentication scheme (Basic, JWT, ...) that has been used.
    @Override
    public String getAuthenticationScheme() {
        return authenticationScheme;
    }
}