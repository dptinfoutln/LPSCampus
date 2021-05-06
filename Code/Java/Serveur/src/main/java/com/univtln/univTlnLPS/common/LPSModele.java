package com.univtln.univTlnLPS.common;

import com.univtln.univTlnLPS.model.carte.Campus;
import com.univtln.univTlnLPS.ressources.administration.AdministrateurResources;
import com.univtln.univTlnLPS.ressources.administration.SuperviseurResources;
import com.univtln.univTlnLPS.ressources.administration.UtilisateurResources;
import com.univtln.univTlnLPS.ressources.carte.BatimentResources;
import com.univtln.univTlnLPS.ressources.carte.CampusResources;
import com.univtln.univTlnLPS.ressources.carte.EtageResources;
import com.univtln.univTlnLPS.ressources.carte.PieceResources;
import com.univtln.univTlnLPS.ressources.scan.ScanDataResources;
import com.univtln.univTlnLPS.ressources.scan.WifiDataResources;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Log
public class LPSModele {

    public static void init() throws IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {

        CampusResources.init();
        AdministrateurResources.init();
        SuperviseurResources.init();
        UtilisateurResources.init();
        BatimentResources.init();
        EtageResources.init();
        PieceResources.init();
        ScanDataResources.init();
        WifiDataResources.init();

    }
}
