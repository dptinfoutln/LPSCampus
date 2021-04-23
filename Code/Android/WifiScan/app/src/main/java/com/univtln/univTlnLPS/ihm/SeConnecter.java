package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;

public class SeConnecter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_connecter);
    }

    public void onClickSeConnecter(View v) {
        Intent i = new Intent(this, AjoutDataOrConsulterData.class);
        startActivity(i);
    }
}