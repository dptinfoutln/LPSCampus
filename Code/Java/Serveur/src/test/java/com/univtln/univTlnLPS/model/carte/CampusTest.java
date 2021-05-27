package com.univtln.univTlnLPS.model.carte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univtln.univTlnLPS.model.administration.Administrateur;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class CampusTest {

    @Test
    public void testCanEqual() {
        assertFalse((new Campus()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        Campus campus = new Campus();
        assertTrue(campus.canEqual(new Campus()));
    }

    @Test
    public void testConstructor() {
        Campus actualCampus = new Campus();
        Administrateur administrateur = new Administrateur();
        actualCampus.setAdministrateur(administrateur);
        HashSet<Batiment> batimentSet = new HashSet<Batiment>();
        actualCampus.setBatimentList(batimentSet);
        actualCampus.setId(123L);
        actualCampus.setName("Name");
        actualCampus.setPlan("Plan");
        assertSame(administrateur, actualCampus.getAdministrateur());
        assertSame(batimentSet, actualCampus.getBatimentList());
        assertEquals(123L, actualCampus.getId());
        assertEquals("Name", actualCampus.getName());
        assertEquals("Plan", actualCampus.getPlan());
        assertEquals("Campus(name=Name, plan=Plan, batimentList=[], id=123, administrateur=Administrateur(campus=null))",
                actualCampus.toString());
    }

    @Test
    public void testConstructor2() {
        HashSet<Batiment> batimentList = new HashSet<Batiment>();
        Campus actualCampus = new Campus("Name", "Plan", batimentList, 123L, new Administrateur());
        assertEquals("Campus(name=Name, plan=Plan, batimentList=[], id=123, administrateur=Administrateur(campus=null))",
                actualCampus.toString());
        assertTrue(actualCampus.getBatimentList().isEmpty());
        assertEquals("Name", actualCampus.getName());
        assertEquals("Plan", actualCampus.getPlan());
        assertEquals(123L, actualCampus.getId());
    }

    @Test
    public void testEquals() {
        assertFalse((new Campus()).equals("42"));
    }

    @Test
    public void testEquals2() {
        Campus campus = new Campus();
        assertTrue(campus.equals(new Campus()));
    }

    @Test
    public void testEquals3() {
        Campus campus = new Campus();
        HashSet<Batiment> batimentList = new HashSet<Batiment>();
        assertFalse(campus.equals(new Campus("Name", "Plan", batimentList, 123L, new Administrateur())));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new Campus()).hashCode());
    }
}

