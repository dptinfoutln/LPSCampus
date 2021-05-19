package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.carte.model.Campus;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class CreerCampus extends AppCompatActivity {

    private EditText nomCamp, url;
    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_campus);

        nomCamp = findViewById(R.id.nomCampus);
        url = findViewById(R.id.UrlCampus);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }


    public void createCampus(View v) throws JSONException {
        String nom, urlPlan;
        nom = nomCamp.getText().toString();
        urlPlan = url.getText().toString();

        JSONObject cid = new JSONObject();


        cid.put("name", nom);
        cid.put("plan", urlPlan);
        cid.put("id", 0);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "campus", cid, !true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}