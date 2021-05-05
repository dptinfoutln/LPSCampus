package com.univtln.univTlnLPS.scan.model;

public class WifiData {

    private long id;
    private String BSSID;
    private String capabilities;
    private int centerFreq0;
    private int centerFreq1;
    private int channelWidth;
    private int frequency;
    private int level;
    private long timestamp;
    private String operatorFriendlyName;
    private String SSID;
    private String venueName;


    public long getId() {
        return id;
    }

    public String getBSSID() {
        return BSSID;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public int getCenterFreq0() {
        return centerFreq0;
    }

    public int getCenterFreq1() {
        return centerFreq1;
    }

    public int getChannelWidth() {
        return channelWidth;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getLevel() {
        return level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOperatorFriendlyName() {
        return operatorFriendlyName;
    }

    public String getSSID() {
        return SSID;
    }

    public String getVenueName() {
        return venueName;
    }


    public WifiData(long id, String BSSID, String capabilities, int centerFreq0, int centerFreq1,
                    int channelWidth, int frequency, int level, long timestamp,
                    String operatorFriendlyName, String SSID, String venueName) {
        this.id = id;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
        this.centerFreq0 = centerFreq0;
        this.centerFreq1 = centerFreq1;
        this.channelWidth = channelWidth;
        this.frequency = frequency;
        this.level = level;
        this.timestamp = timestamp;
        this.operatorFriendlyName = operatorFriendlyName;
        this.SSID = SSID;
        this.venueName = venueName;
    }
}
