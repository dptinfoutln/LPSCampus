package com.univtln.univTlnLPS.client;

import android.media.DrmInitData;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class Connexion implements Serializable {

    private String login;
    private String mdp;
    private String token;

    public static final String uri1 = "https://";
    public static final String uri2 = ":17443/LPS/LaGarde/connexion";
    private final String uri;

    public String getToken() {
        return token;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public Connexion(String ip) {
        this.login = this.mdp = this.token = null;
        this.uri = uri1 + ip + uri2;
    }


    public void setIdentifiants(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
        this.token = null;
    }

    /**
     * S'authentifie pour récupérer le token
     * @return True si l'authentification a réussi
     */
    public boolean seConnecter() {
        String auth = login + ":" + mdp;
        auth = "Basic" + Base64.getEncoder().encodeToString(auth.getBytes());

        boolean success = false;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Authorization", auth);
            urlConnection.setRequestMethod("POST");

            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            switch(status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader((new InputStreamReader(urlConnection.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    token = sb.toString();
                    success = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return success;
    }


}
