package com.univtln.univTlnLPS.Administration.model;

import com.univtln.univTlnLPS.Scan.ScanData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType
//@Entity
public class Utilisateur {
    private String CaracteristiquesMachine;

    private ScanData scan = null;
    //@Id @GeneratedValue
    private long id;
}
