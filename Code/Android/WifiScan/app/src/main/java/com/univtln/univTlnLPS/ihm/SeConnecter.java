package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.Connexion;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class SeConnecter extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    private EditText loginTxt, mdpTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_connecter);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        loginTxt = findViewById(R.id.login);
        mdpTxt = findViewById(R.id.motDePasse);
    }

    public void onClickSeConnecter(View v) throws JSONException {
        v.setEnabled(false);

        Connexion c = ssgbdControleur.getConnexion();
        c.setIdentifiants(loginTxt.getText().toString(), mdpTxt.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (c.seConnecter()) {
                    Intent i = new Intent(SeConnecter.this, PagePrincipaleEP.class);
                    i.putExtra("ssgbdC", ssgbdControleur);
                    startActivity(i);
                }
                else {
                    SeConnecter.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SeConnecter.this, "Identifiant ou mot de passe incorrects", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                SeConnecter.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                });
            }
        }).start();
    }


    public void onClickDevenirSuperviseur(View V) {
        Intent i = new Intent(this, DevenirSuperviseur.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }


}