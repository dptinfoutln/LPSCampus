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

        Intent i = new Intent();
        i.putExtra("ssgbdControleur", ssgbdControleur);
        i.putExtra("ip", String.valueOf(ipTxt));

    }

    private boolean init() {
        String ip = ipTxt.getText().toString();
        if ("".equals(ip))
            return false;
        ssgbdControleur = new SSGBDControleur(ip);
        return true;
    }

    public void onClickAvecConnexion(View v) {
        if (!init())
            return;
        Intent i = new Intent(this, SeConnecter.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void onClickSansConnexion(View v) {
        if (!init())
            return;
        Intent i = new Intent(this, SeLocaliser.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }




}