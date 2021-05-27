package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univtln.univTlnLPS.model.scan.ScanData;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class SuperviseurTest {

    @Test
    public void testConstructor() throws UnsupportedEncodingException {
        Superviseur actualSuperviseur = new Superviseur();
        actualSuperviseur.setEmail("jane.doe@example.org");
        SecureRandom secureRandom = new SecureRandom();
        actualSuperviseur.setRandom(secureRandom);
        actualSuperviseur.setSalt("AAAAAAAA".getBytes("UTF-8"));
        HashSet<ScanData> scanDataSet = new HashSet<ScanData>();
        actualSuperviseur.setScanList(scanDataSet);
        assertEquals("jane.doe@example.org", actualSuperviseur.getEmail());
        assertEquals("jane.doe@example.org", actualSuperviseur.getName());
        assertNull(actualSuperviseur.getPasswordHash());
        assertSame(secureRandom, actualSuperviseur.getRandom());
        assertSame(scanDataSet, actualSuperviseur.getScanList());
        assertEquals("Superviseur(email=jane.doe@example.org, passwordHash=null, salt=[65, 65, 65, 65, 65, 65, 65, 65],"
                + " scanList=[], random=NativePRNG)", actualSuperviseur.toString());
    }

    @Test
    public void testConstructor2() {
        Superviseur actualSuperviseur = new Superviseur();
        assertEquals("Superviseur(email=null, passwordHash=null, salt=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],"
                + " scanList=null, random=NativePRNG)", actualSuperviseur.toString());
        assertEquals(Short.SIZE, actualSuperviseur.getSalt().length);
    }

    @Test
    public void testConstructor3() throws UnsupportedEncodingException {
        byte[] passwordHash = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        byte[] salt = "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8");
        HashSet<ScanData> scanList = new HashSet<ScanData>();
        Superviseur actualSuperviseur = new Superviseur("jane.doe@example.org", passwordHash, salt, scanList,
                new SecureRandom());
        assertEquals(
                "Superviseur(email=jane.doe@example.org, passwordHash=[65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,"
                        + " 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65], salt=[65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65,"
                        + " 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65, 65], scanList=[], random=NativePRNG)",
                actualSuperviseur.toString());
        assertTrue(actualSuperviseur.getScanList().isEmpty());
        assertEquals(24, actualSuperviseur.getSalt().length);
        assertEquals(24, actualSuperviseur.getPasswordHash().length);
        assertEquals("jane.doe@example.org", actualSuperviseur.getEmail());
    }
}

