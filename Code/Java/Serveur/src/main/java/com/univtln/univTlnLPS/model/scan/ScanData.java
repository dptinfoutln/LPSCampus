package com.univtln.univTlnLPS.model.scan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.administration.Utilisateur;
import com.univtln.univTlnLPS.model.carte.Piece;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Set;

/**
 * Classe des Donnees de scan du modele
 */
@Log
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)


@NamedQueries({
        @NamedQuery(name = "scanData.findByPiece", query = "select scanData from ScanData scanData where scanData.piece=:piece"),
        @NamedQuery(name = "scanData.findByUser", query = "select scanData from ScanData scanData where scanData.user=:user"),
        @NamedQuery(name = "scanData.findBySuper", query = "select scanData from ScanData scanData where scanData.superviseur=:superviseur"),
        @NamedQuery(name = "scanData.findBySuperAndPiece", query = "select scanData from ScanData scanData where (scanData.superviseur=:superviseur and scanData.piece=:piece)"),
        @NamedQuery(name = "scanData.findScanPiecesBySuper", query = "select distinct scanData.piece from ScanData scanData where (scanData.superviseur=:superviseur)")})


@Entity
public class ScanData implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @XmlElement
    @Size(max = 30)
    private String infoScan;

    /**
     * The Date scan.
     */
    @XmlElement
    @Temporal(TemporalType.DATE)
    Date dateScan;

    @XmlElement
    @ManyToOne
    private Piece piece;

    /**
     * The Wifi list.
     */
    @XmlElement(name = "Wifi")
    @XmlElementWrapper(name = "Wifis")
    @OneToMany(mappedBy="scanData")
    Set<WifiData> wifiList;

    @JsonIgnore
    @OneToOne(mappedBy = "lastScan")
    private Utilisateur user;

    @JsonIgnore
    @ManyToOne
    private Superviseur superviseur;

}
