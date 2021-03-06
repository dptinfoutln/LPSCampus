package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VoirDemandes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;
    ListView ListeViewDemandes;
    List<String> ListeDemandes;
    AdapterString ListeAdapter;

    private String lastId, nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_demandes);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        lastId = "-1";
        affichageDemandes();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String resultat = (String)parent.getItemAtPosition(position);
        ListeAdapter.setItemSelected(position);
        ListeAdapter.notifyDataSetChanged();
        lastId = resultat.split(":")[0];
        nom = resultat.split(":")[1];
    }


    private void affichageDemandes() {
        ListeAdapter = new AdapterString(this, new ArrayList<>());
        ListeViewDemandes = (ListView) findViewById(R.id.ListeDemandes);
        ListeDemandes = new ArrayList<>();

        // récupération de la liste des pièces
        new Thread(new Runnable() {
            @Override
            public void run() {
                String chaine = null;
                try {
                    chaine = ssgbdControleur.doRequest("GET", "forms", null, !true);
                    JSONObject jchaine = SSGBDControleur.getJSONFromJSONString(chaine);

                    Iterator<String> it = jchaine.keys();
                    String name;
                    while (it.hasNext()) {
                        name = it.next(); // id de l'objet piece
                        name += ":" + ((JSONObject) jchaine.get(name)).getString("email"); // on récupère le nom associé
                        ListeDemandes.add(name);
                    }
                    ListeAdapter = new AdapterString(VoirDemandes.this, ListeDemandes);

                    VoirDemandes.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeViewDemandes.setAdapter(ListeAdapter);
                            ListeViewDemandes.setOnItemClickListener(VoirDemandes.this);
                            ListeViewDemandes.refreshDrawableState();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void refuser(View v) {
        // suppression de la demande
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("DELETE", "forms/" + lastId, null, true);
                    VoirDemandes.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            affichageDemandes();
                            Toast.makeText(VoirDemandes.this, "Demande refusée", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void accepter(View v) {
        // ajout du superviseur à la base de données
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "superviseurs/" + lastId, null, !true);
                    VoirDemandes.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            affichageDemandes();
                            Toast.makeText(VoirDemandes.this, "Demande acceptée", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    
}