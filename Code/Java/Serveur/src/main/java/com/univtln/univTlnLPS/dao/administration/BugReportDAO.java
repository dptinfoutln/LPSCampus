package com.univtln.univTlnLPS.dao.administration;


import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.BugReport;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.scan.WifiData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.Date;
import java.util.List;

@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class BugReportDAO extends AbstractDAO<BugReport> {

    public List<BugReport> findByCat(String cat) {
        return getEntityManager().createNamedQuery("bugReport.findByCat")
                .setParameter("cat", cat)
                .getResultList();
    }

    public List<BugReport> findByDate(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDate")
                .setParameter("date", date)
                .getResultList();
    }

    public List<BugReport> findByDateBelow(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDateBelow")
                .setParameter("date", date)
                .getResultList();
    }

    public List<BugReport> findByDateAbove(Date date) {
        return getEntityManager().createNamedQuery("bugReport.findByDateAbove")
                .setParameter("date", date)
                .getResultList();
    }

    public List<BugReport> findByDateBetween(Date dateDeb, Date dateFin) {
        return getEntityManager().createNamedQuery("bugReport.findByDateBetween")
                .setParameter("dateDeb", dateDeb)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }
}
