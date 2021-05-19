package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class BDD extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_d);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void supprimerCompteSuper(View v) {
        Intent i = new Intent(this, SupprimerCompteSuperviseur.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerCampus(View v) {
        Intent i = new Intent(this, SupprimerCampus.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerBatiment(View v) {
        Intent i = new Intent(this, SupprimerBatiment.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerEtage(View v) {
        Intent i = new Intent(this, SupprimerEtage.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerSalle(View v) {
        Intent i = new Intent(this, SupprimerSalle.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

}