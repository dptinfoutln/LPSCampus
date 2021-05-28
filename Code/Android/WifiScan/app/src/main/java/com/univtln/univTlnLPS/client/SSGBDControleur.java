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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class SSGBDControleur implements Serializable {
    private final String baseUrl;
    private final Connexion c;
    private final String ip;

    public SSGBDControleur(String ip) {
        baseUrl = "https://" + ip + ":17443/LPS/LaGarde/";
        c = new Connexion(ip);
        this.ip = ip;
    }

    public Connexion getConnexion() { return c; }

    public String getIp() {
        return ip;
    }

    public static JSONObject getJSONFromJSONString(String res) throws JSONException {
        return new JSONObject(res);
    }

    public String doRequest(String method, String path, JSONObject param, boolean secured) throws JSONException {
        if (param == null)
            return doRequestStr(method, path, null, secured);
        return doRequestStr(method, path, param.toString(), secured);
    }

    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public String doRequestStr(String method, String path, String param, boolean secured) throws JSONException {

        HttpURLConnection urlConnection = null;
        String res = "";
        try {
            URL url = new URL(baseUrl + path);

            // on ouvre la connection (on paramètre la requete)
            urlConnection = (HttpURLConnection) url.openConnection();

            // si on veut envoyer quelque chose (impossible pour GET/DELETE)
            // note: utiliser GET avec param != null va exécuter un POST
            urlConnection.setDoOutput(param != null);

            if (secured) {
                if (c.getToken() == null)
                    if (!c.seConnecter()){
                        urlConnection.disconnect();
                        return ""; // échec de l'authentification
                    }
                // on met le token dans le header
                urlConnection.setRequestProperty("Authorization", "Bearer" + c.getToken());
            }

            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod(method);

            if (param != null) {
                // on indique dans l'header qu'on envoie du json
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                // on envoie param
                DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                localDataOutputStream.writeBytes(param+"\n");
                localDataOutputStream.flush();
                localDataOutputStream.close();
            }

            // on exécute la requete
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
                    // si on a déjà envoyé le token
                    if (secured) {
                        // echec
                        res =  "";
                    }
                    else{
                        // sinon on réessaie en envoyant le token
                        res =  doRequestStr(method, path, param,true);
                    }
                    break;

                // si le token est invalide ou a expiré
                case 498:
                    // on récupère un nouveau token et on réessaie
                    c.seConnecter();
                    res =  doRequestStr(method, path, param, true);
                    break;

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


}
