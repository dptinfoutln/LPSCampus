package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupprimerCompteSuperviseur extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;

    private ListView ListeViewSuperviseurs;
    private AdapterString ListeAdapter;
    private List<String> list;

    private String role, chaine, lastId, nom;

    private JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_compte_superviseur);

        ListeViewSuperviseurs = findViewById(R.id.listSupprSuper);

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

        // récupération de la liste des users pour l'admin sinon du nom du superviseur
        new Thread(new Runnable() {
            @Override
            public void run() {

                chaine = null;

                list = new ArrayList<>();
                jsonObj = null;
                try {
                    jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "superviseurs", null, true));

                    // remplissage de la liste
                    Iterator<String> it2 = jsonObj.keys();
                    while (it2.hasNext()) {
                        String key = it2.next();
                        list.add(key + ":" + ((JSONObject) jsonObj.get(key)).getString("email"));
                    }
                    ListeAdapter = new AdapterString(SupprimerCompteSuperviseur.this, list);

                    SupprimerCompteSuperviseur.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeViewSuperviseurs.setAdapter(ListeAdapter);
                            ListeViewSuperviseurs.setOnItemClickListener(SupprimerCompteSuperviseur.this);
                            ListeViewSuperviseurs.refreshDrawableState();
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
                    SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("DELETE", "superviseurs/" + lastId, null, true));

                     SupprimerCompteSuperviseur.this.runOnUiThread(new Runnable() {
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