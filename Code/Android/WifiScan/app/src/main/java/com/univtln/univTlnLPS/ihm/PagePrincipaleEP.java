package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class PagePrincipaleEP extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_principale_e_p);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

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


    public void onClickParametres(View v) {
        Intent i = new Intent(this, GestionCompte.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

}