package com.univtln.univTlnLPS.Scan;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WifiData {
    private String BSSID;
    private String Capabilities;
    private int centerFreq0;
    private int centerFreq1;
    private int channelWidth;
    private int frequency;
    private int level;
    private long timestamp;
    private String operatorFriendlyName;
    private String SSID;
    private String venueName;
}
