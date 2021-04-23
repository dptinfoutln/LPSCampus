package com.univtln.univTlnLPS.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSeLocaliser(View v) {
        Intent i = new Intent(this, SeLocaliser.class);
        startActivity(i);
    }

    public void onClickSeConnecter(View v) {
        //Intent i = new Intent(this, AjoutData.class);
        Intent i = new Intent(this, SeConnecter.class);
        startActivity(i);
    }
}