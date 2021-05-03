package com.univtln.univTlnLPS.carte.model;

public class Etage {

    private String nom;
    private long id;
    private String plan;

    public long getId() {
        return id;
    }

    public String getPlan() {
        return plan;
    }

    public Etage(long id, String plan) {
        this.id = id;
        this.plan = plan;
    }


    public Etage(String nom, long id) {
        this.nom = nom;
        this.id = id;
    }
}
