package com.univtln.univTlnLPS.model.administration;

import com.univtln.univTlnLPS.model.carte.Campus;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@Entity
public class Administrateur extends Superviseur {
    @XmlElement
    @OneToOne
    private Campus campus;
}
