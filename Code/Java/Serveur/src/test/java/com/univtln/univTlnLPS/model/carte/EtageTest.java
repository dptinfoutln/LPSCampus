package com.univtln.univTlnLPS.model.carte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class EtageTest {

    @Test
    public void testCanEqual() {
        assertFalse((new Etage()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        Etage etage = new Etage();
        assertTrue(etage.canEqual(new Etage()));
    }

    @Test
    public void testConstructor() {
        Etage actualEtage = new Etage();
        Batiment batiment = new Batiment();
        actualEtage.setBatiment(batiment);
        actualEtage.setId(123L);
        actualEtage.setName("Name");
        HashSet<Piece> pieceSet = new HashSet<Piece>();
        actualEtage.setPieceList(pieceSet);
        actualEtage.setPlan("Plan");
        assertSame(batiment, actualEtage.getBatiment());
        assertEquals(123L, actualEtage.getId());
        assertEquals("Name", actualEtage.getName());
        assertSame(pieceSet, actualEtage.getPieceList());
        assertEquals("Plan", actualEtage.getPlan());
        assertEquals(
                "Etage(plan=Plan, name=Name, id=123, batiment=Batiment(position_x=0, position_y=0, name=null, etageList=null,"
                        + " campus=null, id=0), pieceList=[])",
                actualEtage.toString());
    }

    @Test
    public void testConstructor2() {
        Batiment batiment = new Batiment();
        Etage actualEtage = new Etage("Plan", "Name", 123L, batiment, new HashSet<Piece>());
        assertEquals(
                "Etage(plan=Plan, name=Name, id=123, batiment=Batiment(position_x=0, position_y=0, name=null, etageList=null,"
                        + " campus=null, id=0), pieceList=[])",
                actualEtage.toString());
        assertEquals(123L, actualEtage.getId());
        assertTrue(actualEtage.getPieceList().isEmpty());
        assertEquals("Plan", actualEtage.getPlan());
        assertEquals("Name", actualEtage.getName());
    }

    @Test
    public void testEquals() {
        assertFalse((new Etage()).equals("42"));
    }

    @Test
    public void testEquals2() {
        Etage etage = new Etage();
        assertTrue(etage.equals(new Etage()));
    }

    @Test
    public void testEquals3() {
        Etage etage = new Etage();
        Batiment batiment = new Batiment();
        assertFalse(etage.equals(new Etage("Plan", "Name", 123L, batiment, new HashSet<Piece>())));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new Etage()).hashCode());
    }
}

