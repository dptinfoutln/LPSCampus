package com.univtln.univTlnLPS.dao.administration;


import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.administration.BugReport;
import com.univtln.univTlnLPS.model.administration.Superviseur;
import com.univtln.univTlnLPS.model.scan.WifiData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

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
}
