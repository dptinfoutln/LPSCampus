package com.univtln.univTlnLPS.model.carte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.univtln.univTlnLPS.model.SimpleEntity;
import com.univtln.univTlnLPS.model.administration.Administrateur;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

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
@Entity

@NamedQueries({
        @NamedQuery(name = "campus.findByName",
                query = "select campus " +
                        "from Campus campus " +
                        "where campus.name=:name")})


public class Campus implements SimpleEntity {
    @XmlElement
    private String name;

    @XmlElement
    private String plan;

    @JsonIgnore
    @OneToMany(mappedBy = "campus")
    private Set<Batiment> batimentList;

    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private long id;

    @JsonIgnore
    @ManyToOne
    private Administrateur administrateur;
}
