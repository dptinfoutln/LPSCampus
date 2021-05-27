package com.univtln.univTlnLPS.model.administration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.carte.Campus;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Classe Administrateur du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

@NamedQueries({
        @NamedQuery(name = "administrateur.findByCampus",
                query = "select administrateur from Administrateur administrateur" +
                        " where administrateur.campus=:campus")})
@Entity
public class Administrateur extends Superviseur {
    @JsonIgnore
    @XmlElement(name = "Campus")
    @XmlElementWrapper(name = "CampusList")
    @OneToMany(mappedBy = "administrateur")
    private Set<Campus> campus;
}
