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

public class Connexion implements Serializable {

    private String login;
    private String mdp;
    private String token;

    public static final String uri1 = "http://";
    public static final String uri2 = ":9998/LPS/LaGarde/connexion";
    private final String uri;


    public String getToken() {
        return token;
    }


    public Connexion(String ip) {
        this.login = this.mdp = this.token = null;
        this.uri = uri1 + ip + uri2;
    }


    public void setIdentifiants(String login, String mdp){
        this.login = login;
        this.mdp = mdp;
        this.token = null;
    }


    /**
     * S'authentifie pour récupérer le token
     * @return True si l'authentification a réussi
     * @throws JSONException
     */
    public boolean seConnecter() throws JSONException {
        JSONObject identifiants = new JSONObject();
        identifiants.put("login", login);
        identifiants.put("mdp", mdp);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestMethod("GET");

            DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            localDataOutputStream.writeBytes(identifiants.toString());
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
                        sb.append(line).append("\n");
                    }
                    br.close();
                    token = sb.toString();
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return false;
    }


}
