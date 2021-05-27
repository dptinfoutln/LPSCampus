package com.univtln.univTlnLPS.model.scan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.model.carte.Piece;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ScanDataTest {

    @Test
    public void testCanEqual() {
        assertFalse((new ScanData()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        ScanData scanData = new ScanData();
        assertTrue(scanData.canEqual(new ScanData()));
    }

    @Test
    public void testConstructor() {
        ScanData actualScanData = new ScanData();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date fromResult = Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant());
        actualScanData.setDateScan(fromResult);
        actualScanData.setId(123L);
        actualScanData.setInfoScan("Info Scan");
        Piece piece = new Piece();
        actualScanData.setPiece(piece);
        Superviseur superviseur = new Superviseur();
        actualScanData.setSuperviseur(superviseur);
        Utilisateur utilisateur = new Utilisateur();
        actualScanData.setUser(utilisateur);
        HashSet<WifiData> wifiDataSet = new HashSet<WifiData>();
        actualScanData.setWifiList(wifiDataSet);
        assertSame(fromResult, actualScanData.getDateScan());
        assertEquals(123L, actualScanData.getId());
        assertEquals("Info Scan", actualScanData.getInfoScan());
        assertSame(piece, actualScanData.getPiece());
        assertSame(superviseur, actualScanData.getSuperviseur());
        assertSame(utilisateur, actualScanData.getUser());
        assertSame(wifiDataSet, actualScanData.getWifiList());
        assertEquals(
                "ScanData(id=123, infoScan=Info Scan, dateScan=Thu Jan 01 00:00:00 UTC 1970, piece=Piece(position_x=0,"
                        + " position_y=0, name=null, id=0, etage=null, scanList=null), wifiList=[], user=Utilisateur(id=0,"
                        + " caracteristiquesMachine=null, lastScan=null), superviseur=Superviseur(email=null, passwordHash=null,"
                        + " salt=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], scanList=null, ",
                actualScanData.toString().replace(" CET ", " UTC ").split("random")[0]);
    }

    @Test
    public void testConstructor2() {
        Date dateScan = new Date(1L);
        Piece piece = new Piece();
        HashSet<WifiData> wifiList = new HashSet<WifiData>();
        Utilisateur user = new Utilisateur();
        ScanData actualScanData = new ScanData(123L, "Info Scan", dateScan, piece, wifiList, user, new Superviseur());
        assertEquals(
                "ScanData(id=123, infoScan=Info Scan, dateScan=Thu Jan 01 01:00:00 UTC 1970, piece=Piece(position_x=0,"
                        + " position_y=0, name=null, id=0, etage=null, scanList=null), wifiList=[], user=Utilisateur(id=0,"
                        + " caracteristiquesMachine=null, lastScan=null), superviseur=Superviseur(email=null, passwordHash=null,"
                        + " salt=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], scanList=null, ",
                actualScanData.toString().replace(" CET ", " UTC ").split("random")[0]);
        assertEquals(123L, actualScanData.getId());
        assertEquals("Info Scan", actualScanData.getInfoScan());
        assertTrue(actualScanData.getWifiList().isEmpty());
    }

    @Test
    public void testEquals() {
        assertFalse((new ScanData()).equals("42"));
    }

    @Test
    public void testEquals2() {
        ScanData scanData = new ScanData();
        assertTrue(scanData.equals(new ScanData()));
    }

    @Test
    public void testEquals3() {
        ScanData scanData = new ScanData();
        Date dateScan = new Date(1L);
        Piece piece = new Piece();
        HashSet<WifiData> wifiList = new HashSet<WifiData>();
        Utilisateur user = new Utilisateur();
        assertFalse(scanData.equals(new ScanData(123L, "Info Scan", dateScan, piece, wifiList, user, new Superviseur())));
    }

    @Test
    public void testHashCode() {
        assertEquals(59, (new ScanData()).hashCode());
    }
}

