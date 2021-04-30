package com.univtln.univTlnLPS.model.administration;

import com.univtln.univTlnLPS.model.carte.Campus;
import jakarta.persistence.*;
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

@NamedQueries({
        @NamedQuery(name = "administrateur.findByID",
                query = "select Administrateur from Administrateur admin" +
                        " where admin.id=:id"),
        @NamedQuery(name = "administrateur.update",
                query = "update Administrateur admin" +
                        " set admin.campus=:campus," +
                        " admin.email=:email," +
                        " admin.passwordHash=:pWH," +
                        " admin.caracteristiquesMachine=:caract," +
                        " admin.scan=:scan" +
                        " where admin.id=:id"),
        @NamedQuery(name = "administrateur.delete",
                query = "delete from Administrateur admin" +
                        " where admin.id=:id")})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Administrateur extends Superviseur {
    @XmlElement
    @OneToOne
    private Campus campus;
}
