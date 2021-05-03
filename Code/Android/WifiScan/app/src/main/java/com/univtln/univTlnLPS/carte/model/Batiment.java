package com.univtln.univTlnLPS.carte.model;

import java.util.List;

public class Batiment {

    private String nom;
    private long id;
    private List<Etage> listEtages;

    public Batiment(String nom, long id, List<Etage> listEtages) {
        this.nom = nom;
        this.id = id;
        this.listEtages = listEtages;
    }

    public Batiment(String nom, long id) {
        this.nom = nom;
        this.id = id;
    }
}
