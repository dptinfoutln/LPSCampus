package com.univtln.univTlnLPS.Carte.resources;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import jakarta.ws.rs.NotFoundException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Campus {

    //@PUT
    //@Path("initCampus")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Carte.model.Campus campus =
                new com.univtln.univTlnLPS.Carte.model.Campus();
        campus.builder().batimentList(new ArrayList<>()).build();
    }

}
