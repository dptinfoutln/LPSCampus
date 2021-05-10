package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class ModifierMDP extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private EditText ancien, nouveau, confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_m_d_p);

        ancien = findViewById(R.id.ancien);
        nouveau = findViewById(R.id.nouveau);
        confirmation = findViewById(R.id.confirmation);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void valider(View v) {
        // vérifier que ancien correspond bien au mot de passe
        if (ancien.getText().toString().equals(ssgbdControleur.getConnexion().getMdp())) {
            // vérifier que nouveau et confirmation égaux
            if (nouveau.getText().toString().equals(confirmation.getText().toString())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ssgbdControleur.doRequest("POST", "superviseurs/mdp/" + nouveau, null, !true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

    }

}