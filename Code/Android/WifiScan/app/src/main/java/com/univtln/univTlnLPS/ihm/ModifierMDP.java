package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        if (nouveau.getText().toString().length() > 4) {
            // vérifier que ancien correspond bien au mot de passe
            if (ancien.getText().toString().equals(ssgbdControleur.getConnexion().getMdp())) {
                // vérifier que nouveau et confirmation égaux
                if (nouveau.getText().toString().equals(confirmation.getText().toString())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ssgbdControleur.doRequestStr("POST", "superviseurs/me/mdp", nouveau.getText().toString(), true);
                                ModifierMDP.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ModifierMDP.this, "Votre mot de passe a été modifié", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(ModifierMDP.this, "Les nouveaux mots de passe rentrés sont différents", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ModifierMDP.this, "Votre mot de passe est incorrect", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(ModifierMDP.this, "Le mot de passe doit avoir au minimum 5 caractères", Toast.LENGTH_LONG).show();
        }
    }

}