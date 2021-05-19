package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupprimerCampus extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;

    private ListView ListeViewCampus;
    private AdapterString ListeAdapter;
    private List<String> list;

    private String role, chaine, lastId, nom;

    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_campus);

        ListeViewCampus = findViewById(R.id.listSupprCampus);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        affichageSuperviseurs();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String resultat = (String) parent.getItemAtPosition(position);
        lastId = resultat.split(":")[0];
        nom = resultat.split(":")[1];

        ListeAdapter.setItemSelected(position);
        ListeAdapter.notifyDataSetChanged();
    }

    public void affichageSuperviseurs() {
        ListeAdapter = new AdapterString(this, new ArrayList<>());
        ListeViewCampus = (ListView) findViewById(R.id.listedonneessalle);

        // récupération de la liste des users pour l'admin sinon du nom du superviseur
        new Thread(new Runnable() {
            @Override
            public void run() {

                chaine = null;

                list = new ArrayList<>();
                jsonObj = null;
                try {
                    jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "campus", null, true));

                    // remplissage de la liste
                    Iterator<String> it2 = jsonObj.keys();
                    while (it2.hasNext()) {
                        String key = it2.next();
                        list.add(key + ":" + ((JSONObject) jsonObj.get(key)).getString("email"));
                    }
                    ListeAdapter = new AdapterString(SupprimerCampus.this, list);

                    SupprimerCampus.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeViewCampus.setAdapter(ListeAdapter);
                            ListeViewCampus.setOnItemClickListener(SupprimerCampus.this);
                            ListeViewCampus.refreshDrawableState();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void supprimer(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("DELETE", "campus/" + lastId, null, true));

                    SupprimerCampus.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            affichageSuperviseurs();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}