package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.carte.model.Piece;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.Adapter.AdapterSalles;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AffichageDonneesSalle extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    ListView ListeDonneesSalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_donnees_salle);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void affichageDonneedSalle(View v) throws JSONException {
        ListAdapter listeAdapter = new AdapterSalles(this, new ArrayList<>());
        ListeDonneesSalle = (ListView) findViewById(R.id.listedonneessalle);
        //Piece piece = new Piece();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String chaine = null;
                try {
                    chaine = ssgbdControleur.doRequest("pieces/", null, "GET", !true);
                    JSONObject jchaine = SSGBDControleur.getJSONFromJSONString(chaine);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // remplir la listeAdapter avec la liste des salles grace à la BDD Java
        // donner à la liste view la liste adpater
        // rendre la liste view cliquable
    }

}