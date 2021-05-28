package com.univtln.univTlnLPS.client;

import android.net.wifi.ScanResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Position {

    public static String uri1 = "https://";
    public static String uri2 = ":17443/position";

    public static String get(String uri, JSONObject obj){
        HttpURLConnection urlConnection = null;
        String res = "";
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestMethod("POST");

            DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            localDataOutputStream.writeBytes(obj.toString());
            localDataOutputStream.flush();
            localDataOutputStream.close();

            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            switch(status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader((new InputStreamReader(urlConnection.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    res = sb.toString();
            }
                    } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return res;
    }

    /*
    JSONObject jsonObject = new JSONObject();
try {
        jsonObject.put("firstname", "Amit");
        jsonObject.put("lastname", "Shekhar");
    } catch (JSONException e) {
        e.printStackTrace();
    }*/

    /*
    transformer une liste de scan en json.

     */

    /**
    fonction qui convertit une liste de scan result en json

     */
    public static JSONObject convertScan(List<ScanResult> scanRes) throws JSONException {
        //" { scan1 : {attr1 : val1 ...}}"
        JSONObject res = new JSONObject();
        for (int i = 0; i < scanRes.size(); i++) {
            JSONObject res2 = new JSONObject();
            ScanResult scan = scanRes.get(i);
            res2.put("BSSID", scan.BSSID);
            res2.put("capabilities", scan.capabilities);
            res2.put("centerFreq0", scan.centerFreq0);
            res2.put("centerFreq1", scan.centerFreq1);
            res2.put("channelWidth", scan.channelWidth);
            res2.put("frequency", scan.frequency);
            res2.put("level", scan.level);
            res2.put("timestamp", scan.timestamp);
            res2.put("operatorFriendlyName", scan.operatorFriendlyName);
            res2.put("SSID", scan.SSID);
            res2.put("venueName", scan.venueName);
            res.put("wifi"+i, res2);
        }
        return res;

    }

}
