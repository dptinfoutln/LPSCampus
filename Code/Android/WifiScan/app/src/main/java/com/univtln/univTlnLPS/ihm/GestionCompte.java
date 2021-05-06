package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class GestionCompte extends AppCompatActivity {


    private SSGBDControleur ssgbdControleur;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_compte);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
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