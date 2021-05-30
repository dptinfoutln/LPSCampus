package com.univtln.univTlnLPS.dao.scan;

import com.univtln.univTlnLPS.dao.AbstractDAO;
import com.univtln.univTlnLPS.model.scan.ScanData;
import com.univtln.univTlnLPS.model.scan.WifiData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

import java.util.List;


/**
 * DAO des Donnees de Wifi
 */
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
public class WifiDataDAO extends AbstractDAO<WifiData> {
    public List<WifiData> findByScanData(ScanData scanData) {
        return getEntityManager().createNamedQuery("wifiData.findByScanData")
                .setParameter("scanData", scanData)
                .getResultList();
    }
}