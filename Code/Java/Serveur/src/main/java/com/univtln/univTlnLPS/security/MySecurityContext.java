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
import java.util.List;

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
            List<Superviseur> liste = superviseurDAO.findByEmail(userEmail);
            if (!liste.isEmpty())
                superviseur = liste.get(0);
        }
        return superviseur;

    }


    //A method to check if a user belongs to a role
    @Override
    public boolean isUserInRole(String role) {
        // On recupere le superviseur correspondant a l'email
        Superviseur superviseur = null;
        try (SuperviseurDAO superviseurDAO = SuperviseurDAO.of()) {
            List<Superviseur> liste = superviseurDAO.findByEmail(userEmail);
            if (!liste.isEmpty())
                superviseur = liste.get(0);
        }
        return BasicAuthenticationFilter.isUserInRole(role, superviseur);

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