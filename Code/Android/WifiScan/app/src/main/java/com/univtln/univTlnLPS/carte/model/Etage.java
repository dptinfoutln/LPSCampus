package com.univtln.univTlnLPS.carte.model;

public class Etage {

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
}
