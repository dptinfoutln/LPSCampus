package com.univtln.univTlnLPS.model.carte;

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
        @NamedQuery(name = "batiment.findByName", query = "select batiment from Batiment batiment where batiment.name=:name")})

public class Batiment {

    @XmlElement
    private String name;

    @XmlElement(name = "Etage")
    @XmlElementWrapper(name = "Etages")
    @OneToMany(mappedBy="bat")
    private Set<Etage> etageList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;
}
