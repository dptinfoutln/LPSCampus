package com.univtln.univTlnLPS.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class HomeActivity extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    private EditText ipTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ipTxt = findViewById(R.id.ip);
    }

    private boolean init() {
        String ip = ipTxt.getText().toString();
        if ("".equals(ip))
            return false;
        ssgbdControleur = new SSGBDControleur(ip);
        return true;
    }

    public void onClickSeLocaliser(View v) {
        if (!init())
            return;
        Intent i = new Intent(this, SeLocaliser.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickSeConnecter(View v) {
        if (!init())
            return;
        //Intent i = new Intent(this, AjoutData.class);
        Intent i = new Intent(this, SeConnecter.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickDevenirSuperviseur(View V) {
        if (!init())
            return;
        Intent i = new Intent(this, DevenirSuperviseur.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }
}