package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterSalles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AffichageDonneesSalle extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;
    private ListView ListeDonneesSalle;
    private ListAdapter listeAdapter;
    private List<String> liste;
    private TextView nomSalle;

    private String lastId, salleId, nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_donnees_salle);

        nomSalle = findViewById(R.id.nomSalle);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
        salleId = i.getStringExtra("salleId");
        nom = i.getStringExtra("piece");

        try {
            affichageDonneesSalle();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String resultat = (String)parent.getItemAtPosition(position);
        lastId = resultat.split(":")[0];
    }


    public void affichageDonneesSalle() throws JSONException {
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
                        JSONObject tmp = (JSONObject)jchaine.get(name);
                        name = name + ":" + tmp.get("infoScan").toString(); // on récupère le nom associé
                        liste.add(name);
                    }
                    listeAdapter = new AdapterSalles(AffichageDonneesSalle.this, liste);

                    AffichageDonneesSalle.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeDonneesSalle.setAdapter(listeAdapter);
                            ListeDonneesSalle.setOnItemClickListener(AffichageDonneesSalle.this);
                            nomSalle.setText(nom);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void afficher(View v) {
        Intent i = new Intent(this, AffichageDetailDonnees.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        i.putExtra("lastId", lastId);
        startActivity(i);
    }


    public void supprimer(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("DELETE", "scans/" + lastId, null, !true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}