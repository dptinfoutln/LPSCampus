package com.univtln.univTlnLPS.model.administration;

import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.scan.ScanData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe Utilisateur du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedQueries({
        @NamedQuery(name = "utilisateur.findByCaract",
                query = "select utilisateur from Utilisateur utilisateur" +
                        " where utilisateur.caracteristiquesMachine=:caract"),
        @NamedQuery(name = "utilisateur.findByScan",
                query = "select utilisateur from Utilisateur utilisateur" +
                        " where utilisateur.lastScan=:scan")})

@Entity
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Utilisateur implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @XmlElement
    @NotNull
    @Size(min = 2, max = 10)
    private String caracteristiquesMachine;

    @XmlElement
    @OneToOne
    private ScanData lastScan;

    /**
     * The enum Role.
     */
    public enum Role {
        /**
         * Admin role.
         */
        ADMIN,
        /**
         * Supervisor role.
         */
        SUPER,
        /**
         * USER role.
         */
        USER
    }

}
