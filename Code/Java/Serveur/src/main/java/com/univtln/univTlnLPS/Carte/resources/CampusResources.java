package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import com.univtln.univTlnLPS.Carte.model.Campus;
import jakarta.ws.rs.NotFoundException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CampusResources {

    //@PUT
    //@Path("initCampus")
    public void init() throws IllegalArgumentException {
        Campus.builder().batimentList(new ArrayList<>()).build();
    }

}
