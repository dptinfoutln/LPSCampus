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
public class Batiment {

    @XmlElement(name = "Etage")
    @XmlElementWrapper(name = "Etages")
    @OneToMany
    private Set<Etage> etageList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;
}
