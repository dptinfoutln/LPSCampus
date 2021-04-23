package com.univtln.univTlnLPS.Scan.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType
//@Entity
public class ScanData {
    private String infoScan;

    List<WifiData> wifiList;

    private long id;
}
