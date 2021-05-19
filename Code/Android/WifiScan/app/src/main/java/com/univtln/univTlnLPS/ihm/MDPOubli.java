package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class MDPOubli extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_d_p_oubli);

        mail = findViewById(R.id.mail);


        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }


    public void envoyer(View v) throws JSONException {
        JSONObject pid = new JSONObject();

        pid.put("id", 0);
        pid.put("category", "MotDePasseOublie");
        pid.put("content", mail.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "bugReports", pid, !true);

                    MDPOubli.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MDPOubli.this, "Demande de nouveau mot de passe envoyée", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}