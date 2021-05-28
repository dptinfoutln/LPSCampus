package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifierLogin extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private EditText nouveau;

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
                    ssgbdControleur.doRequestStr("POST", "superviseurs/me/login", nouveau.getText().toString(), true);
                    ModifierLogin.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ModifierLogin.this, "Votre login a été modifié", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}