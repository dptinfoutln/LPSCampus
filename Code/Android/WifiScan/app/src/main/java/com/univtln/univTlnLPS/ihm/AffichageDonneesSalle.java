package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterSalles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AffichageDonneesSalle extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    ListView ListeDonneesSalle;
    private ListAdapter listeAdapter;
    private List<String> liste;

    private String salleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_donnees_salle);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
        salleId = i.getStringExtra("salleId");

        try {
            affichageDonneedSalle();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void affichageDonneedSalle() throws JSONException {
        listeAdapter = new AdapterSalles(this, new ArrayList<>());
        ListeDonneesSalle = (ListView) findViewById(R.id.listedonneessalle);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String chaine = null;
                try {
                    chaine = ssgbdControleur.doRequest("GET", "pieces/" + salleId + "/scans", null, !true);
                    JSONObject jchaine = SSGBDControleur.getJSONFromJSONString(chaine);

                    liste = new ArrayList<>();

                    Iterator<String> it = jchaine.keys();
                    String name;
                    while (it.hasNext()) {
                        name = it.next(); // id de l'objet piece
                        name = ((JSONObject)jchaine.get(name)).toString(); // on récupère le nom associé
                        liste.add(name);
                    }
                    listeAdapter = new AdapterSalles(AffichageDonneesSalle.this, liste);

                    AffichageDonneesSalle.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeDonneesSalle.setAdapter(listeAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}