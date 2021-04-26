package com.univtln.univTlnLPS.Scan.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType
//@Entity
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

    private long id;
}
