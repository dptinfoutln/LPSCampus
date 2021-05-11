package com.univtln.univTlnLPS.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * source : https://developer.android.com/guide/topics/connectivity/wifi-scan
 */
public class WifiScan {
    private final WifiManager wifiManager;
    private boolean results = false;
    private boolean failure = false;
    private List<ScanResult> scanResults;

    public WifiScan(Context context) {
        scanResults = new ArrayList<>();

        wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();
                }
                else{
                    failure = true;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);
    }

    /**
     *
     * @return True si le scan a réussi
     */
    public boolean startScan(){
        return wifiManager.startScan();
    }

    /**
     *
     * @return True si un nouveau résultat est disponible
     */
    public boolean isNewScanResultsAvailable(){
        return results;
    }

    public boolean hasFailed(){
        boolean res = failure;
        failure = false;
        return res;
    }

    /**
     *
     * @return Les derniers résultats de scan.
     */
    public List<ScanResult> getResults(){
        results = false;
        return scanResults;
    }

    private void scanSuccess() {
        results = true;
        scanResults = wifiManager.getScanResults();
    }
}
