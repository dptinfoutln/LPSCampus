package com.univtln.univTlnLPS.Carte.model;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.Image;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
public class Campus {
    @XmlElement
    private Image plan;

    @XmlElement(name = "Batiment")
    @XmlElementWrapper(name = "Batiments")
    private List<Batiment> batimentList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    //@Id @GeneratedValue
    private long id;
}
