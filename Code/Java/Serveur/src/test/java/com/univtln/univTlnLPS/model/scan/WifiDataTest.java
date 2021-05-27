package com.univtln.univTlnLPS.model.scan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WifiDataTest {
    @Test
    public void testBuilder() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        WifiData.builder();
    }

    @Test
    public void testCanEqual() {
        assertFalse((new WifiData()).canEqual("Other"));
    }

    @Test
    public void testCanEqual2() {
        WifiData wifiData = new WifiData();
        assertTrue(wifiData.canEqual(new WifiData()));
    }

    @Test
    public void testConstructor() {
        WifiData actualWifiData = new WifiData();
        actualWifiData.setBSSID("BSSID");
        actualWifiData.setCapabilities("Capabilities");
        actualWifiData.setCenterFreq0(1);
        actualWifiData.setCenterFreq1(1);
        actualWifiData.setChannelWidth(1);
        actualWifiData.setFrequency(1);
        actualWifiData.setId(123L);
        actualWifiData.setLevel(1);
        actualWifiData.setOperatorFriendlyName("Operator Friendly Name");
        actualWifiData.setSSID("SSID");
        ScanData scanData = new ScanData();
        actualWifiData.setScanData(scanData);
        actualWifiData.setTimestamp(10L);
        actualWifiData.setVenueName("Venue Name");
        assertEquals("BSSID", actualWifiData.getBSSID());
        assertEquals("Capabilities", actualWifiData.getCapabilities());
        assertEquals(1, actualWifiData.getCenterFreq0());
        assertEquals(1, actualWifiData.getCenterFreq1());
        assertEquals(1, actualWifiData.getChannelWidth());
        assertEquals(1, actualWifiData.getFrequency());
        assertEquals(123L, actualWifiData.getId());
        assertEquals(1, actualWifiData.getLevel());
        assertEquals("Operator Friendly Name", actualWifiData.getOperatorFriendlyName());
        assertEquals("SSID", actualWifiData.getSSID());
        assertSame(scanData, actualWifiData.getScanData());
        assertEquals(10L, actualWifiData.getTimestamp());
        assertEquals("Venue Name", actualWifiData.getVenueName());
        assertEquals(
                "WifiData(id=123, BSSID=BSSID, Capabilities=Capabilities, centerFreq0=1, centerFreq1=1, channelWidth=1,"
                        + " frequency=1, level=1, timestamp=10, operatorFriendlyName=Operator Friendly Name, SSID=SSID, venueName=Venue"
                        + " Name, scanData=ScanData(id=0, infoScan=null, dateScan=null, piece=null, wifiList=null, user=null,"
                        + " superviseur=null))",
                actualWifiData.toString());
    }

    @Test
    public void testConstructor2() {
        WifiData actualWifiData = new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L, "Operator Friendly Name",
                "SSID", "Venue Name", new ScanData());
        assertEquals("BSSID", actualWifiData.getBSSID());
        assertEquals(
                "WifiData(id=123, BSSID=BSSID, Capabilities=Capabilities, centerFreq0=1, centerFreq1=1, channelWidth=1,"
                        + " frequency=1, level=1, timestamp=10, operatorFriendlyName=Operator Friendly Name, SSID=SSID, venueName=Venue"
                        + " Name, scanData=ScanData(id=0, infoScan=null, dateScan=null, piece=null, wifiList=null, user=null,"
                        + " superviseur=null))",
                actualWifiData.toString());
        assertEquals("Venue Name", actualWifiData.getVenueName());
        assertEquals(10L, actualWifiData.getTimestamp());
        assertEquals("SSID", actualWifiData.getSSID());
        assertEquals("Capabilities", actualWifiData.getCapabilities());
        assertEquals(1, actualWifiData.getCenterFreq1());
        assertEquals(1, actualWifiData.getLevel());
        assertEquals("Operator Friendly Name", actualWifiData.getOperatorFriendlyName());
        assertEquals(1, actualWifiData.getChannelWidth());
        assertEquals(1, actualWifiData.getCenterFreq0());
        assertEquals(1, actualWifiData.getFrequency());
        assertEquals(123L, actualWifiData.getId());
    }

    @Test
    public void testEquals() {
        assertFalse((new WifiData()).equals("42"));
    }

    @Test
    public void testEquals2() {
        WifiData wifiData = new WifiData();
        assertTrue(wifiData.equals(new WifiData()));
    }

    @Test
    public void testEquals3() {
        WifiData wifiData = new WifiData();
        assertFalse(wifiData.equals(new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L,
                "Operator Friendly Name", "SSID", "Venue Name", new ScanData())));
    }

    @Test
    public void testEquals4() {
        WifiData wifiData = new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L, "Operator Friendly Name",
                "SSID", "Venue Name", new ScanData());
        assertTrue(wifiData.equals(new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L, "Operator Friendly Name",
                "SSID", "Venue Name", new ScanData())));
    }

    @Test
    public void testEquals5() {
        WifiData wifiData = new WifiData();
        assertFalse(wifiData.equals(new WifiData(0L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L, "Operator Friendly Name",
                "SSID", "Venue Name", new ScanData())));
    }

    @Test
    public void testEquals6() {
        WifiData wifiData = new WifiData(123L, "com.univtln.univTlnLPS.model.scan.WifiData", "Capabilities", 1, 1, 1, 1, 1,
                10L, "Operator Friendly Name", "SSID", "Venue Name", new ScanData());
        assertFalse(wifiData.equals(new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L,
                "Operator Friendly Name", "SSID", "Venue Name", new ScanData())));
    }

    @Test
    public void testHashCode() {
        assertEquals(3524, (new WifiData()).hashCode());
        assertEquals(63517871, (new WifiData(123L, "BSSID", "Capabilities", 1, 1, 1, 1, 1, 10L, "Operator Friendly Name",
                "SSID", "Venue Name", new ScanData())).hashCode());
    }
}

