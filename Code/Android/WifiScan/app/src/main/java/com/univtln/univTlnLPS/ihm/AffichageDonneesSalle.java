package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.ihm.Adapter.AdapterSalles;

import java.util.ArrayList;

public class AffichageDonneesSalle extends AppCompatActivity {

    ListView ListeDonneesSalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_donnees_salle);
    }

    public void affichageSalle(View v) {
        ListAdapter listeAdapter = new AdapterSalles(this, new ArrayList<>());
        ListeDonneesSalle = (ListView) findViewById(R.id.listedonneessalle);
        // remplir la listeAdapter avec la liste des salles grace à la BDD Java
        // donner à la liste view la liste adpater
        // rendre la liste view cliquable
    }

}