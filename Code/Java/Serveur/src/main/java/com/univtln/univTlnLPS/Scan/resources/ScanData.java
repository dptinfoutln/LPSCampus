package com.univtln.univTlnLPS.Scan.resources;

import com.univtln.univTlnLPS.Scan.model.WifiData;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.ArrayList;
import java.util.List;

public class ScanData {


    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Scan.model.ScanData modelScanData =
            com.univtln.univTlnLPS.Scan.model.ScanData.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Scan.model.ScanData> scandatas =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initScanData")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Scan.model.ScanData scandata =
                com.univtln.univTlnLPS.Scan.model.ScanData.of();
        scandata.builder().infoScan("").wifiList(new ArrayList<>()).build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Scan.model.ScanData addScanData(com.univtln.univTlnLPS.Scan.model.ScanData scandata) throws IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(++lastId);
        scandatas.put(scandata.getId(), scandata);
        return scandata;
    }

    public com.univtln.univTlnLPS.Scan.model.ScanData updateScanData(long id, com.univtln.univTlnLPS.Scan.model.ScanData scandata) throws NotFoundException, IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(id);
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.put(id, scandata);
        return scandata;
    }

    public void removeScanData(long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        scandatas.remove(id);
    }

    public com.univtln.univTlnLPS.Scan.model.ScanData getScanData(long id) throws NotFoundException {
        if (!scandatas.containsKey(id)) throw new NotFoundException();
        return scandatas.get(id);
    }

    public int getScanDataSize() {
        return scandatas.size();
    }

    public void deleteScanDatas() {
        scandatas.clear();
        lastId = 0;
    }
}
