package com.univtln.univTlnLPS.Scan.resources;

import com.univtln.univTlnLPS.Scan.model.ScanData;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

import java.util.ArrayList;

public class ScanDataResources {


    private static long lastId = 0;

    private static final ScanData modelScanData = new ScanData();

    final MutableLongObjectMap<ScanData> scandatas =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initScanData")
    public void init() throws IllegalArgumentException {
        ScanData.builder().infoScan("").wifiList(new ArrayList<>()).build();
    }

    // add delete update

    public ScanData addScanData(ScanData scandata) throws IllegalArgumentException {
        if (scandata.getId() != 0) throw new IllegalArgumentException();
        scandata.setId(++lastId);
        scandatas.put(scandata.getId(), scandata);
        return scandata;
    }

    public ScanData updateScanData(long id, ScanData scandata) throws NotFoundException, IllegalArgumentException {
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

    public ScanData getScanData(long id) throws NotFoundException {
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
