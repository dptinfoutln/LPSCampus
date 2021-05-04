package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class ReporterProbleme extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_probleme);

        // recuperer la selection

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void valider(View v) {
        // seulement si quelque chose est coché
        Intent i = new Intent(this, DescriptionProbleme.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }
}