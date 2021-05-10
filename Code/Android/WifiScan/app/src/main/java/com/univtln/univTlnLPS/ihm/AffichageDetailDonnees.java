package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class AffichageDetailDonnees extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private TextView texte, affichage;
    private String lastId, texteaffichage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_donnees);

        texte = findViewById(R.id.texteDonnee);
        affichage = findViewById(R.id.affichage);

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
                    AffichageDetailDonnees.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            affichage.setText(texteaffichage);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}