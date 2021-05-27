package com.univtln.univTlnLPS.dao.administration;


import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.BugReport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.Date;
import java.util.List;

/**
 * DAO des Rapports de Bugs
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class BugReportDAO extends AbstractDAO<BugReport> {

    /**
     * Find by cat list.
     *
     * @param cat the cat
     * @return liste des Rapports de Bug ayant pour categorie cat
     */
    public List<BugReport> findByCat(String cat) {
        return getEntityManager().createNamedQuery("bugReport.findByCat")
                .setParameter("cat", cat)
                .getResultList();
    }

    /**
     * Find all cat list.
     *
     * @return la liste des categories des Rapports de Bugs
     */
    public List<String> findAllCat() {
        return getEntityManager().createNamedQuery("bugReport.findCategories")
                .getResultList();
    }

    /**
     * Find by date list.
     *
     * @param date the date
     * @return liste des Rapports de Bug datant du jour date
     */
    public List<BugReport> findByDate(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDate")
                .setParameter("date", date)
                .getResultList();
    }

    /**
     * Find by date below list.
     *
     * @param date the date
     * @return liste des Rapports de Bug datant d'avant date
     */
    public List<BugReport> findByDateBelow(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDateBelow")
                .setParameter("date", date)
                .getResultList();
    }

    /**
     * Find by date above list.
     *
     * @param date the date
     * @return liste des Rapports de Bug datant d'apres date
     */
    public List<BugReport> findByDateAbove(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDateAbove")
                .setParameter("date", date)
                .getResultList();
    }

    /**
     * Find by date between by cat list.
     *
     * @param dateDeb the date deb
     * @param dateFin the date fin
     * @param cat     the cat
     * @return liste des Rapports de Bug envoyes entre dateDeb et dateFin
     */
    public List<BugReport> findByDateBetweenByCat(Date dateDeb, Date dateFin, String cat) {
        return getEntityManager().createNamedQuery("bugReport.findByDateBetweenByCat")
                .setParameter("dateDeb", dateDeb)
                .setParameter("dateFin", dateFin)
                .setParameter("cat", cat)
                .getResultList();
    }
}
