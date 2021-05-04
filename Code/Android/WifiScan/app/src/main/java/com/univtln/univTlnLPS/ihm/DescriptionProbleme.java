package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.univtln.univTlnLPS.R;

public class DescriptionProbleme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_probleme);
    }

    public void validerpb(View v) {
        // recuperer selection radiobutton
        // envoyer à la bdd le probleme avec la description
    }
}