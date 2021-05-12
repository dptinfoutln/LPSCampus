package com.univtln.univTlnLPS.common;

import com.univtln.univTlnLPS.dao.DAO;
import com.univtln.univTlnLPS.dao.administration.UtilisateurDAO;
import com.univtln.univTlnLPS.dao.carte.BatimentDAO;
import com.univtln.univTlnLPS.dao.carte.CampusDAO;
import com.univtln.univTlnLPS.dao.carte.EtageDAO;
import com.univtln.univTlnLPS.dao.carte.PieceDAO;
import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.ressources.administration.AdministrateurResources;
import com.univtln.univTlnLPS.ressources.administration.SuperviseurResources;
import com.univtln.univTlnLPS.ressources.administration.UtilisateurResources;
import com.univtln.univTlnLPS.ressources.carte.BatimentResources;
import com.univtln.univTlnLPS.ressources.carte.CampusResources;
import com.univtln.univTlnLPS.ressources.carte.EtageResources;
import com.univtln.univTlnLPS.ressources.carte.PieceResources;
import com.univtln.univTlnLPS.ressources.scan.ScanDataResources;
import com.univtln.univTlnLPS.ressources.scan.WifiDataResources;
import jakarta.persistence.EntityTransaction;
import lombok.extern.java.Log;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Log
public class LPSModele {

    public static <T extends SimpleEntity> void deleteAll(DAO<T> dao){
        EntityTransaction transaction = dao.getTransaction();
        transaction.begin();
        dao.deleteAll();
        transaction.commit();
    }

    public static void init() throws IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {

        deleteAll(PieceDAO.of());
        deleteAll(EtageDAO.of());
        deleteAll(BatimentDAO.of());
        deleteAll(CampusDAO.of());
        deleteAll(UtilisateurDAO.of());

        AdministrateurResources.init();
        SuperviseurResources.init();
        UtilisateurResources.init();

        CampusResources.init();
        BatimentResources.init();
        EtageResources.init();
        PieceResources.init();

        ScanDataResources.init();
        WifiDataResources.init();

    }
}
