package com.univtln.univTlnLPS.Carte.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor(staticName = "of")
@Getter
@Setter
@Builder
@XmlRootElement
@XmlAccessorType
//@Entity
public class Piece {
    public int[] position;

    //@Id @GeneratedValue
    private long id;
}
