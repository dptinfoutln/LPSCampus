package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.univtln.univTlnLPS.model.carte.Campus;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class AdministrateurTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Administrateur.builder();
    }

    @Test
    public void testConstructor() {
        Administrateur actualAdministrateur = new Administrateur();
        HashSet<Campus> campusSet = new HashSet<Campus>();
        actualAdministrateur.setCampus(campusSet);
        assertSame(campusSet, actualAdministrateur.getCampus());
        assertEquals("Administrateur(campus=[])", actualAdministrateur.toString());
    }

    @Test
    public void testConstructor2() {
        assertEquals(Short.SIZE, (new Administrateur()).getSalt().length);
    }
}

