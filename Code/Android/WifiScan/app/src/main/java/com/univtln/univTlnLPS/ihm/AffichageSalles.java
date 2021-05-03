package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterSalles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AffichageSalles extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;
    ListView ListeViewSalles;
    List<String> ListeSalles;
    ListAdapter ListeAdapter;

    private String lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_salles);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        lastId = "-1";
        affichageSalle();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String resultat = (String)parent.getItemAtPosition(position);
        lastId = resultat.split(":")[0];
    }


    public void affichageSalle() {
        ListeAdapter = new AdapterSalles(this, new ArrayList<>());
        ListeViewSalles = (ListView) findViewById(R.id.listedonneessalle);
        ListeSalles = new ArrayList<>();

        // récupération de la liste des pièces
        new Thread(new Runnable() {
            @Override
            public void run() {
                String chaine = null;
                try {
                    chaine = ssgbdControleur.doRequest("GET", "pieces", null, !true);
                    JSONObject jchaine = SSGBDControleur.getJSONFromJSONString(chaine);

                    Iterator<String> it = jchaine.keys();
                    String name;
                    while (it.hasNext()) {
                        name = it.next(); // id de l'objet piece
                        name += ":" + ((JSONObject)jchaine.get(name)).getString("name"); // on récupère le nom associé
                        ListeSalles.add(name);
                    }
                    ListeAdapter = new AdapterSalles(AffichageSalles.this, ListeSalles);

                    AffichageSalles.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeViewSalles.setAdapter(ListeAdapter);
                            ListeViewSalles.setOnItemClickListener(AffichageSalles.this);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onClickChoisir(View v) {
        if (!"-1".equals(lastId)) {
            Intent i = new Intent(this, AffichageDonneesSalle.class);
            i.putExtra("ssgbdC", ssgbdControleur);
            i.putExtra("salleId", lastId);
            startActivity(i);
        }
    }
}