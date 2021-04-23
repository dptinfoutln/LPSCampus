package com.univtln.univTlnLPS.Scan.resources;

import com.univtln.univTlnLPS.Carte.model.Piece;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.impl.factory.primitive.LongObjectMaps;

public class WifiData {


    private static long lastId = 0;

    private static final com.univtln.univTlnLPS.Scan.model.WifiData modelData =
            com.univtln.univTlnLPS.Scan.model.WifiData.of();

    final MutableLongObjectMap<com.univtln.univTlnLPS.Scan.model.WifiData> wifidatas =
            LongObjectMaps.mutable.empty();


    //@PUT
    //@Path("initWifiData")
    public void init() throws IllegalArgumentException {
        com.univtln.univTlnLPS.Scan.model.WifiData wifidata =
                com.univtln.univTlnLPS.Scan.model.WifiData.of();
        wifidata.builder().BSSID("").build();
    }

    // add delete update

    public com.univtln.univTlnLPS.Scan.model.WifiData addWifiData(com.univtln.univTlnLPS.Scan.model.WifiData wifidata) throws IllegalArgumentException {
        if (wifidata.getId() != 0) throw new IllegalArgumentException();
        wifidata.setId(++lastId);
        wifidatas.put(wifidata.getId(), wifidata);
        return wifidata;
    }

    public com.univtln.univTlnLPS.Scan.model.WifiData updateWifiData(long id, com.univtln.univTlnLPS.Scan.model.WifiData wifidata) throws NotFoundException, IllegalArgumentException {
        if (wifidata.getId() != 0) throw new IllegalArgumentException();
        wifidata.setId(id);;
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        wifidatas.put(id, wifidata);
        return wifidata;
    }

    public void removeWifiData(long id) throws NotFoundException {
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        wifidatas.remove(id);
    }

    public com.univtln.univTlnLPS.Scan.model.WifiData getWifiData(long id) throws NotFoundException {
        if (!wifidatas.containsKey(id)) throw new NotFoundException();
        return wifidatas.get(id);
    }

    public int getWifiDataSize() {
        return wifidatas.size();
    }

    public void deleteWifiData() {
        wifidatas.clear();
        lastId = 0;
    }
}
