package com.univtln.univTlnLPS.Administration.model;

import com.univtln.univTlnLPS.Scan.model.ScanData;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
public class Utilisateur {
    @XmlElement
    private String CaracteristiquesMachine;

    @XmlElement
    private ScanData scan = null;

    @XmlAttribute
    @EqualsAndHashCode.Include
    //@Id @GeneratedValue
    // changer le id en uuid
    private long id;


}
