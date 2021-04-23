package com.univtln.univTlnLPS.Administration.resources;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("utilisateur")
@XmlRootElement
public class Utilisateur {

    //@PUT
    //@Path("initUtilisateur")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Administration.model.Superviseur superviseur =
                new com.univtln.univTlnLPS.Administration.model.Superviseur();
        superviseur.builder().CaracteristiquesMachine("").build();
    }
}
