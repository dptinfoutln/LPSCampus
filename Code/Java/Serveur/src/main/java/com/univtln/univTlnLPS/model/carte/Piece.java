package com.univtln.univTlnLPS.model.carte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

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

@NamedQueries({
        @NamedQuery(name = "piece.findByName",
                query = "select piece " +
                        "from Piece piece " +
                        "where piece.name=:name")})

public class Piece implements SimpleEntity {

    @XmlElement
    private int position_x;
    @XmlElement
    private int position_y;

    @XmlElement
    private String name;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    @XmlElement
    @ManyToOne
    private Etage etage;

    @JsonIgnore
    @OneToMany(mappedBy="piece")
    private Set<ScanData> scanList;
}
