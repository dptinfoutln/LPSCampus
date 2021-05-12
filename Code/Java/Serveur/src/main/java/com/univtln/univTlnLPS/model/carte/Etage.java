package com.univtln.univTlnLPS.model.carte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Log
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter

@Setter
@Builder

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity


@NamedQueries({
        @NamedQuery(name = "etage.findByName", query = "select etage from Etage etage where etage.name=:name")})

public class Etage implements SimpleEntity {

    @XmlElement
    private String plan;

    @XmlElement
    private String name;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne
    private Batiment batiment;

    @XmlElement(name = "piece")
    @XmlElementWrapper(name = "pieces")
    @OneToMany(mappedBy="etage")
    private Set<Piece> pieceList;
}
