package com.univtln.univTlnLPS.model.carte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Classe Batiment du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity


@NamedQueries({
        @NamedQuery(name = "batiment.findByName",
                query = "select batiment " +
                        "from Batiment batiment " +
                        "where batiment.name=:name")})

public class Batiment implements SimpleEntity {

    @XmlElement
    private int position_x;
    @XmlElement
    private int position_y;

    @XmlElement
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="batiment")
    private Set<Etage> etageList;

    @ManyToOne
    @XmlElement
    private Campus campus;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;
}
