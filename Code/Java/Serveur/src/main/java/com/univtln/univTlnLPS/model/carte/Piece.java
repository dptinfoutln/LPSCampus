package com.univtln.univTlnLPS.model.carte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.*;
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
@Entity

@NamedQueries({
        @NamedQuery(name = "piece.findById", query = "select piece from Piece piece where piece.id=:id")})

public class Piece implements SimpleEntity {

    @XmlElement
    private int position_x;
    @XmlElement
    private int position_y;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne
    private Etage etage;

    @XmlElement(name = "scan")
    @XmlElementWrapper(name = "scans")
    @OneToMany(mappedBy="scan")
    private Set<ScanData> scanList;
}
