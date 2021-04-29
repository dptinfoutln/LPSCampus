package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.Adapter.AdapterSalles;

import java.util.ArrayList;

public class AffichageSalles extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    ListView ListeSalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_salles);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }


    public void affichageSalle(View v) {
        ListAdapter listeAdapter = new AdapterSalles(this, new ArrayList<>());
        ListeSalles = (ListView) findViewById(R.id.listedonneessalle);
        // remplir la listeAdapter avec la liste des salles grace à la BDD Java
        // donner à la liste view la liste adpater
        // rendre la liste view cliquable
    }

    public void onClickChoisir(View v) {
        Intent i = new Intent(this, AffichageDonneesSalle.class);
        startActivity(i);
    }
}