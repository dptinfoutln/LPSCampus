package com.univtln.univTlnLPS.dao.scan;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.model.scan.WifiData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class WifiDataDAO extends AbstractDAO<WifiData> {
    public List<WifiData> findById(long id) {
        return getEntityManager().createNamedQuery("wifiData.findById")
                .setParameter("id", id)
                .getResultList();
    }
}