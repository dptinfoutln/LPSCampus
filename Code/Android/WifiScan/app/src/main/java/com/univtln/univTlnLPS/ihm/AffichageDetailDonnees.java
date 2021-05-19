package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.ScanJsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AffichageDetailDonnees extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private TextView texte;
    private ListView affichage;
    private String lastId, texteaffichage;
    private List<JSONObject> listJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_donnees);

        texte = findViewById(R.id.texteDonnee);
        affichage = findViewById(R.id.scanList2);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
        lastId = i.getStringExtra("lastId");

        try {
            affichage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    
    public void affichage() throws JSONException {
        texte.setText("Détail de la donnée : " + lastId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    texteaffichage = ssgbdControleur.doRequest("GET", "scans/" + lastId, null, !true);

                    JSONObject j = SSGBDControleur.getJSONFromJSONString(texteaffichage);
                    JSONArray ja = (JSONArray) j.get("wifiList");
                    listJson = new ArrayList<>();

                    for (int i = 0; i < ja.length(); i++) {
                        listJson.add( (JSONObject) ja.get(i) );
                    }

                    AffichageDetailDonnees.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ScanJsonAdapter adapter = new ScanJsonAdapter(AffichageDetailDonnees.this, listJson);
                            affichage.setAdapter(adapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}