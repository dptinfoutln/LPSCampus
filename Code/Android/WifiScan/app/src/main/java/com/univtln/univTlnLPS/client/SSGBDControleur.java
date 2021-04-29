package com.univtln.univTlnLPS.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class SSGBDControleur implements Serializable {
    private final String baseUrl;
    private final Connexion c;
    private final String ip;

    public SSGBDControleur(String ip) {
        baseUrl = "http://" + ip + ":9998/LPS/LaGarde/";
        c = new Connexion(ip);
        this.ip = ip;
    }

    public Connexion getConnexion() { return c; }

    public String getIp() {
        return ip;
    }

    public JSONObject getObjectFromJSONString(String res) throws JSONException {
        return new JSONObject(res);
    }

    public String doRequest(String path, JSONObject param, String method, boolean secured) throws JSONException {
        if (secured) {
            if (c.getToken() == null)
                if (!c.seConnecter())
                    return ""; // échec de l'authentification
            param.put("token", c.getToken());
        }

        HttpURLConnection urlConnection = null;
        String res = "";
        try {
            URL url = new URL(baseUrl + path);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestMethod(method);

            DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            localDataOutputStream.writeBytes(param.toString());
            localDataOutputStream.flush();
            localDataOutputStream.close();

            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            if (status >= 200 && status <= 299)
                status = 200;
            switch(status) {
                case 200:
                    BufferedReader br = new BufferedReader((new InputStreamReader(urlConnection.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    res = sb.toString();
                    break;

                case 401:
                case 403:
                    if (secured) {
                        return "";
                    }
                    return doRequest(path, param, method, true);

                // si le token est invalide ou a expiré
                case 498:
                    c.seConnecter();
                    return doRequest(path, param, method, true);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }

        return "";
    }


}
