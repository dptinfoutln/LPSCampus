package com.univtln.univTlnLPS.Carte.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType
//@Entity
public class Batiment {
    private List<Etage> etageList;

    //@Id @GeneratedValue
    private long id;
}
