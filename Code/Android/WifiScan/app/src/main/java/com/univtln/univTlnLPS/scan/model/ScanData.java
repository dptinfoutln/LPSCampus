package com.univtln.univTlnLPS.scan.model;

import java.util.Set;

public class ScanData {
    private long id;
    private String infoScan;
    private Set<WifiData> wifiList;

    public long getId() {
        return id;
    }

    public String getInfoScan() {
        return infoScan;
    }

    public Set<WifiData> getWifiList() {
        return wifiList;
    }

    public ScanData(long id, String infoScan, Set<WifiData> wifiList) {
        this.id = id;
        this.infoScan = infoScan;
        this.wifiList = wifiList;
    }

}
