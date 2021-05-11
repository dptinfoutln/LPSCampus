package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class ModifierInfo extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_info);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }


    public void modifierLogin(View v) {
        Intent i = new Intent(this, ModifierLogin.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

    public void modifierMDP(View v) {
        Intent i = new Intent(this, ModifierMDP.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }


}