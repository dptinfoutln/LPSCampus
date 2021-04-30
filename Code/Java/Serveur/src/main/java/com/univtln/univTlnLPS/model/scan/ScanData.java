package com.univtln.univTlnLPS.model.scan;

import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.carte.Etage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)


@NamedQueries({
        @NamedQuery(name = "scanData.findById", query = "select scanData from ScanData scanData where scanData.id=:id")})


@Entity
public class ScanData implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @XmlElement
    @Size(max = 30)
    private String infoScan;

    @XmlElement(name = "Wifi")
    @XmlElementWrapper(name = "Wifis")
    @OneToMany(mappedBy="scanData")
    Set<WifiData> wifiList;


}
