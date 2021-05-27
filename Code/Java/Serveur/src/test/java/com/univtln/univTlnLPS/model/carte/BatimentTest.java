package com.univtln.univTlnLPS.model.carte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BatimentTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testBuilder2() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testBuilder3() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testBuilder4() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testBuilder5() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testBuilder6() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Batiment.builder();
    }

    @Test
    public void testCanEqual() {
        assertFalse((new Batiment()).canEqual("Other"));
        assertFalse((new Batiment()).canEqual("Other"));
        assertFalse((new Batiment()).canEqual("Other"));
        assertFalse((new Batiment()).canEqual("Other"));
        assertFalse((new Batiment()).canEqual("Other"));
        assertFalse((new Batiment()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testCanEqual3() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testCanEqual4() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testCanEqual5() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testCanEqual6() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testCanEqual7() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.canEqual(new Batiment()));
    }

    @Test
    public void testConstructor() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor10() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertEquals(campus, campus2);
        assertSame(campus1, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor11() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor12() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertSame(campus1, campus2);
        assertEquals(campus, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor2() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertSame(campus1, campus2);
        assertEquals(campus, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor3() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor4() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertSame(campus1, campus2);
        assertEquals(campus, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor5() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor6() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertSame(campus1, campus2);
        assertEquals(campus, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor7() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor8() {
        HashSet<Etage> etageList = new HashSet<Etage>();
        Campus campus = new Campus();
        Batiment actualBatiment = new Batiment(1, 1, "Name", etageList, campus, 123L);
        Campus campus1 = new Campus();
        actualBatiment.setCampus(campus1);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        Campus campus2 = actualBatiment.getCampus();
        assertSame(campus1, campus2);
        assertEquals(campus, campus2);
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testConstructor9() {
        Batiment actualBatiment = new Batiment();
        Campus campus = new Campus();
        actualBatiment.setCampus(campus);
        HashSet<Etage> etageSet = new HashSet<Etage>();
        actualBatiment.setEtageList(etageSet);
        actualBatiment.setId(123L);
        actualBatiment.setName("Name");
        actualBatiment.setPosition_x(1);
        actualBatiment.setPosition_y(1);
        assertSame(campus, actualBatiment.getCampus());
        assertSame(etageSet, actualBatiment.getEtageList());
        assertEquals(123L, actualBatiment.getId());
        assertEquals("Name", actualBatiment.getName());
        assertEquals(1, actualBatiment.getPosition_x());
        assertEquals(1, actualBatiment.getPosition_y());
        assertEquals("Batiment(position_x=1, position_y=1, name=Name, etageList=[], campus=Campus(name=null, plan=null,"
                + " batimentList=null, id=0, administrateur=null), id=123)", actualBatiment.toString());
    }

    @Test
    public void testEquals() {
        assertFalse((new Batiment()).equals("42"));
        assertFalse((new Batiment()).equals("42"));
        assertFalse((new Batiment()).equals("42"));
        assertFalse((new Batiment()).equals("42"));
        assertFalse((new Batiment()).equals("42"));
        assertFalse((new Batiment()).equals("42"));
    }

    @Test
    public void testEquals10() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals11() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testEquals12() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals13() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testEquals2() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals3() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testEquals4() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals5() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testEquals6() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals7() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testEquals8() {
        Batiment batiment = new Batiment();
        assertTrue(batiment.equals(new Batiment()));
    }

    @Test
    public void testEquals9() {
        Batiment batiment = new Batiment();
        HashSet<Etage> etageList = new HashSet<Etage>();
        assertFalse(batiment.equals(new Batiment(1, 1, "Name", etageList, new Campus(), 123L)));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new Batiment()).hashCode());
        assertEquals(59, (new Batiment()).hashCode());
        assertEquals(59, (new Batiment()).hashCode());
        assertEquals(59, (new Batiment()).hashCode());
        assertEquals(59, (new Batiment()).hashCode());
        assertEquals(59, (new Batiment()).hashCode());
    }
}

