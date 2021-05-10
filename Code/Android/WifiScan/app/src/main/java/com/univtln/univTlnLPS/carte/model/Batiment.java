package com.univtln.univTlnLPS.carte.model;

import java.util.List;

public class Batiment {

    private long id;
    private String nom;
    private List<Etage> listEtages;
    private int position_x;
    private int position_y;


    public Batiment(long id, String nom, int position_x, int position_y) {
        this.id = id;
        this.nom = nom;
        this.position_x = position_x;
        this.position_y = position_y;
    }

    public Batiment(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

}
