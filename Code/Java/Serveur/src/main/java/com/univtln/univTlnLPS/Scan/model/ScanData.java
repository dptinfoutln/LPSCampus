package com.univtln.univTlnLPS.Scan.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
public class ScanData {

    @XmlElement
    private String infoScan;

    @XmlElement(name = "Wifi")
    @XmlElementWrapper(name = "Wifis")
    List<WifiData> wifiList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    private long id;
}
