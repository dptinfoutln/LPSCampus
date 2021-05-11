package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifierLogin extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private EditText nouveau;
    private JSONObject id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_login);

        nouveau = findViewById(R.id.nouveau);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void valider(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String text = ssgbdControleur.doRequest("GET", "superviseurs/login/" + ssgbdControleur.getConnexion().getLogin(),
                            null, !true);
                    id = SSGBDControleur.getJSONFromJSONString(text);
                    ssgbdControleur.doRequest("POST", "superviseurs/login/" + nouveau, null, !true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}