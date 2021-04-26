package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Administration.model.Administrateur;
import com.univtln.univTlnLPS.Carte.model.Campus;
import org.apache.tapestry5.annotations.Path;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("admin")
@XmlRootElement
//@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class AdministrateurResources {

    //@PUT
    //@Path("initAdmin")
    public void init() throws IllegalArgumentException {
        Administrateur.builder().campus(new Campus()).loginHash("").passwordHash("").build();
    }

}
