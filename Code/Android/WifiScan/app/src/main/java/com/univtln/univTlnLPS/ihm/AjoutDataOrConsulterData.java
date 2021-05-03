package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class AjoutDataOrConsulterData extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_data_or_consulter_data);

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
}