package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class GestionCompte extends AppCompatActivity {


    private SSGBDControleur ssgbdControleur;
    private String id, role;
    private Button suppr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_compte);

        suppr = findViewById(R.id.supprimer);


        suppr.setVisibility(View.INVISIBLE);
        suppr.setEnabled(false);
        suppr.refreshDrawableState();

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    role = ssgbdControleur.doRequest("GET", "superviseurs/me/role", null, !true);
                    role = role.substring(0, role.length()-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                GestionCompte.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (role.equals("SUPER")) {
                            suppr.setVisibility(View.VISIBLE);
                            suppr.setEnabled(true);
                            suppr.refreshDrawableState();
                        }
                    }
                });
            }
        }).start();
    }



    public void modifier(View v) {
        Intent i = new Intent(this, ModifierInfo.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void supprimer(View v) {
        Intent i = new Intent(this, SupprimerCompte.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }


}