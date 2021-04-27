package com.univtln.univTlnLPS.Scan.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
public class WifiData {

    @XmlElement
    private String BSSID;

    @XmlElement
    private String Capabilities;

    @XmlElement
    private int centerFreq0;

    @XmlElement
    private int centerFreq1;

    @XmlElement
    private int channelWidth;

    @XmlElement
    private int frequency;

    @XmlElement
    private int level;

    @XmlElement
    private long timestamp;

    @XmlElement
    private String operatorFriendlyName;

    @XmlElement
    private String SSID;

    @XmlElement
    private String venueName;

    @XmlAttribute
    @EqualsAndHashCode.Include
    private long id;
}
