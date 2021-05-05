package com.univtln.univTlnLPS.carte.model;

import java.util.ArrayList;
import java.util.List;

public class Campus {

    private long id;
    private String nom;
    private List<Batiment> listBatiments;
    private String plan;

    public Campus(long id, String nom, List<Batiment> listBatiments, String plan) {
        this.id = id;
        this.nom = nom;
        this.listBatiments = listBatiments;
        this.plan = plan;
    }

    public Campus(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

}
