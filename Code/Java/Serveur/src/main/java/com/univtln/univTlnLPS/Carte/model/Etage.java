package com.univtln.univTlnLPS.Carte.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.Image;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType
//@Entity
public class Etage {
    private Image plan;

    //@Id @GeneratedValue
    private long id;
}
