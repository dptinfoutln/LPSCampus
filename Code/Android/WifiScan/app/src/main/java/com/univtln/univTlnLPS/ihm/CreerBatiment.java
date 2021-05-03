package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.carte.model.Batiment;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class CreerBatiment extends AppCompatActivity {

    private EditText nomBat;
    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_batiment);

        nomBat = findViewById(R.id.nomBatiment);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void createBatiment(View v) throws JSONException {
        String nom = nomBat.getText().toString();
        JSONObject bid = new JSONObject();

        bid.put("name", nom);
        // faire la liste des campus
        //bid.put()

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "batiments", bid, !true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}