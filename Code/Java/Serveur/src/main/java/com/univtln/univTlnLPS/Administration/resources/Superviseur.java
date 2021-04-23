package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Carte.Campus;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("superviseur")
@XmlRootElement
public class Superviseur {

    //@PUT
    //@Path("initSuperviseur")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Administration.model.Superviseur superviseur =
                new com.univtln.univTlnLPS.Administration.model.Superviseur();
        superviseur.builder().loginHash("").passwordHash("").build();
    }
}
