package com.univtln.univTlnLPS.model.administration;

import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedQueries({
        @NamedQuery(name = "utilisateur.findByID", query = "select utilisateur from Utilisateur utilisateur where utilisateur.id=:id"),
        @NamedQuery(name = "utilisateur.findByCaract", query = "select utilisateur from Utilisateur utilisateur where utilisateur.caracteristiquesMachine=:caract"),
        @NamedQuery(name = "utilisateur.findByScanId", query = "select utilisateur from Utilisateur utilisateur, in (utilisateur.scan) scan where scan.id=:idScan"),
        @NamedQuery(name = "utilisateur.update", query = "update Utilisateur user set user.caracteristiquesMachine=:caract, user.scan=:scan where user.id=:id"),
        @NamedQuery(name = "utilisateur.delete", query = "delete from Utilisateur utilisateur where utilisateur.id=:id")})

@Entity

public class Utilisateur implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @XmlElement
    @NotNull
    //@Size(min = 2, max = 10)
    private String caracteristiquesMachine;

    @XmlElement
    @OneToOne
    private ScanData scan;


    public enum Role {
        /**
         * Admin role.
         */
        ADMIN,
        /**
         * User role.
         */
        USER,
        /**
         * Guest role.
         */
        GUEST
    }

}
