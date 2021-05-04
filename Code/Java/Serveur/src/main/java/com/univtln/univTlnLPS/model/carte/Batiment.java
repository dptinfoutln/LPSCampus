package com.univtln.univTlnLPS.model.carte;

import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Log
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity


@NamedQueries({
        @NamedQuery(name = "batiment.findByName", query = "select batiment from Batiment batiment where batiment.name=:name")})

public class Batiment implements SimpleEntity {

    @XmlElement
    private int position_x;
    @XmlElement
    private int position_y;

    @XmlElement
    private String name;

    @XmlElement(name = "Etage")
    @XmlElementWrapper(name = "Etages")
    @OneToMany(mappedBy="batiment")
    private Set<Etage> etageList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;
}
