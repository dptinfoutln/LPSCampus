package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class SupprimerCompte extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private String id;
    private CheckBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_compte);

        box = findViewById(R.id.checkboxValider);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void valider(View v) {
        if (box.isChecked()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ssgbdControleur.doRequest("DELETE", "superviseurs/me", null, !true);
                        SupprimerCompte.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SupprimerCompte.this, "Votre compte LPSCampus a été supprimé", Toast.LENGTH_LONG).show();
                                SupprimerCompte.this.finishAffinity();
                            }
                        });
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            }).start();
        }
    }

}