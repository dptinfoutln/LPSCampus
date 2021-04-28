package com.univtln.univTlnLPS.Administration.model;

import com.univtln.univTlnLPS.Scan.model.ScanData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.eclipse.persistence.annotations.PrimaryKey;

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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Utilisateur {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @XmlElement
    @NotNull
    //@Size(min = 2, max = 10)
    private String CaracteristiquesMachine;

    @XmlElement
    @OneToOne
    private ScanData scan;
}
