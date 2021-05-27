package com.univtln.univTlnLPS.model.scan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)


@Entity
public class WifiData implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @XmlElement
    @Size(max = 20)
    @EqualsAndHashCode.Include
    private String BSSID;

    @XmlElement
    @Size(max = 60)
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
    @Size(max = 20)
    private String operatorFriendlyName;

    @XmlElement
    @Size(max = 30)
    private String SSID;

    @XmlElement
    @Size(max = 20)
    private String venueName;

    @JsonIgnore
    @ManyToOne
    private ScanData scanData;
}
