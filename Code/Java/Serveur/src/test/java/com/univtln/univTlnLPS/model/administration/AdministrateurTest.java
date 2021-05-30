package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.univtln.univTlnLPS.model.carte.Campus;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class AdministrateurTest {

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

