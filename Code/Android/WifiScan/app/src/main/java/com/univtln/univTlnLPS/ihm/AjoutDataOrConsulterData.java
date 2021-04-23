package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;

public class AjoutDataOrConsulterData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_data_or_consulter_data);
    }

    public void onClickAjouterDonnees(View v) {
        Intent i = new Intent(this, AjoutData.class);
        startActivity(i);
    }

    public void onClickConsulterDonnees(View v) {
        Intent i = new Intent(this, AffichageSalles.class);
        startActivity(i);
    }
}