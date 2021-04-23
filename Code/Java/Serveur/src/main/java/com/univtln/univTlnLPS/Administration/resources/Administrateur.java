package com.univtln.univTlnLPS.Administration.resources;

import com.univtln.univTlnLPS.Carte.Campus;
import org.apache.tapestry5.annotations.Path;

import javax.xml.bind.annotation.XmlRootElement;

//@Path("admin")
@XmlRootElement
//@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class Administrateur {

    //@PUT
    //@Path("initAdmin")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Administration.model.Administrateur admin =
                new com.univtln.univTlnLPS.Administration.model.Administrateur();
        admin.builder().campus(new Campus()).loginHash("").passwordHash("").build();
    }

}
