package com.univtln.univTlnLPS.model.administration;


import com.univtln.univTlnLPS.model.SimpleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;
import lombok.extern.java.Log;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Log
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@ToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

@NamedQueries({
        @NamedQuery(name = "bugReport.findByCat",
                query = "select bugReport from BugReport bugReport" +
                        " where bugReport.category=:cat"),
        @NamedQuery(name = "bugReport.findByDate",
                query = "select bugReport from BugReport bugReport" +
                        " where bugReport.date=:date"),
        @NamedQuery(name = "bugReport.findByDateBelow",
                query = "select bugReport from BugReport bugReport" +
                        " where bugReport.date<:date"),
        @NamedQuery(name = "bugReport.findByDateAbove",
                query = "select bugReport from BugReport bugReport" +
                        " where bugReport.date >: date"),
        @NamedQuery(name = "bugReport.findByDateBetween",
                query = "select bugReport from BugReport bugReport" +
                        " where bugReport.date >: dateDeb and bugReport.date <: dateFin")})

public class BugReport implements SimpleEntity {
    @XmlAttribute
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @XmlElement
    @NotNull
    private String category;

    @XmlElement
    @NotNull
    private String Content;

    @XmlElement
    @NotNull
    //@Size(min = 2, max = 10)
    private String caracteristiquesMachine;

    /*@XmlElement
    private int year;

    @XmlElement
    private int month;

    @XmlElement
    private int day;*/

    @XmlElement
    @Temporal(TemporalType.DATE)
    Date date;


}
