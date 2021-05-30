package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.Position;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class PagePrincipaleEP extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private Button bdd;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_principale_e_p);

        bdd = findViewById(R.id.gererBDD);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        bdd.setVisibility(View.INVISIBLE);
        bdd.setEnabled(false);
        bdd.refreshDrawableState();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    role = ssgbdControleur.doRequest("GET", "superviseurs/me/role", null, !true);
                    role = role.substring(0, role.length()-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                PagePrincipaleEP.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (role.equals("ADMIN")) {
                            bdd.setVisibility(View.VISIBLE);
                            bdd.setEnabled(true);
                            bdd.refreshDrawableState();
                        }
                    }
                });
            }
        }).start();

    }

    public void onClickAjouterDonnees(View v) {
        Intent i = new Intent(this, AjoutData.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickConsulterDonnees(View v) {
        Intent i = new Intent(this, AffichageSalles.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickVoirDemandes(View v) {
        Intent i = new Intent(this, VoirDemandes.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickVoirRapportsBugs(View v) {
        Intent i = new Intent(this, VoirRapportsBugs.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }


    public void onClickParametres(View v) {
        Intent i = new Intent(this, GestionCompte.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickDeconnexion(View v) {
        Toast.makeText(PagePrincipaleEP.this, "Vous êtes déconnecté", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickBDD(View v) {
        Intent i = new Intent(this, BDD.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickEntrainer(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Position.train(Position.uri1 + ssgbdControleur.getIp() + Position.uri2);
            }
        });
    }

}