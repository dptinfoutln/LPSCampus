package com.univtln.univTlnLPS.Administration.model;

import com.univtln.univTlnLPS.Carte.model.Campus;
import jakarta.ws.rs.Path;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class Administrateur extends Superviseur {
    @XmlElement
    private Campus campus;
}
