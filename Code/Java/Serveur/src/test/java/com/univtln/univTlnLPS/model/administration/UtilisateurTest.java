package com.univtln.univTlnLPS.model.administration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.univtln.univTlnLPS.model.scan.ScanData;
import org.junit.jupiter.api.Test;

public class UtilisateurTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        Utilisateur.builder();
    }

    @Test
    public void testConstructor() {
        Utilisateur actualUtilisateur = new Utilisateur();
        actualUtilisateur.setCaracteristiquesMachine("Caracteristiques Machine");
        actualUtilisateur.setId(123L);
        ScanData scanData = new ScanData();
        actualUtilisateur.setLastScan(scanData);
        assertEquals("Caracteristiques Machine", actualUtilisateur.getCaracteristiquesMachine());
        assertEquals(123L, actualUtilisateur.getId());
        assertSame(scanData, actualUtilisateur.getLastScan());
        assertEquals(
                "Utilisateur(id=123, caracteristiquesMachine=Caracteristiques Machine, lastScan=ScanData(id=0,"
                        + " infoScan=null, dateScan=null, piece=null, wifiList=null, user=null, superviseur=null))",
                actualUtilisateur.toString());
    }
}

