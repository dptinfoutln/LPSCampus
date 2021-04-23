package com.univtln.univTlnLPS.Carte.model;

import com.univtln.univTlnLPS.Carte.model.Batiment;
import lombok.*;

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
@XmlAccessorType
//@Entity
public class Campus {
    private Image plan;
    private List<Batiment> batimentList;

    //@Id @GeneratedValue
    private long id;
}
