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
import org.json.JSONObject;

public class DevenirSuperviseur extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private EditText loginTxt, mdpTxt, mdpTxt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devenir_superviseur);

        loginTxt = findViewById(R.id.loginTxt);
        mdpTxt = findViewById(R.id.mdpTxt);
        mdpTxt2 = findViewById(R.id.mdpTxt2);


        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void checkForm(View v) throws JSONException {
        String login = loginTxt.getText().toString();
        String mdp = mdpTxt.getText().toString();
        String mdp2 = mdpTxt2.getText().toString();

        JSONObject form = new JSONObject();

        // Verifions que le mail ait ete renseigne
        if (! login.contains("@") && (! login.contains("univ-tln.fr"))) {
            // Affichage d'un probleme et l'utilisateur reste sur la page
            Toast.makeText(this, "Adresse email non reconnue", Toast.LENGTH_LONG).show();
            return;
        }
        // Verifions que le mot de passe a plus de 5 caracteres
        if (mdp.length() > 4) {
            // Vérifions que l'utilisateur ne s'est pas trompé
            if (mdp.equals(mdp2)) {
                form.put("email", login);
                form.put("password", mdp);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (ssgbdControleur.doRequest("PUT", "forms", form, true).equals("")) {
                                DevenirSuperviseur.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DevenirSuperviseur.this, "Email déjà utilisé", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else {
                                DevenirSuperviseur.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DevenirSuperviseur.this, "Demande envoyée", Toast.LENGTH_LONG).show();
                                        // Retour a la page principale
                                        finish();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else {
                Toast.makeText(DevenirSuperviseur.this, "Les mots de passe rentrés sont différents", Toast.LENGTH_LONG).show();
            }

        }

        else {
            // Affichage temporaire mot de passe incorrect
            Toast.makeText(this, "Mot de passe trop court", Toast.LENGTH_LONG).show();
        }
    }
}