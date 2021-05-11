package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class DescriptionProbleme extends AppCompatActivity {

    private EditText description;
    private String pb;
    private SSGBDControleur ssgbdControleur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_probleme);

        description = findViewById(R.id.description);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
        String pb = i.getStringExtra("radioGroupId");
    }

    public void validerpb(View v) throws JSONException {
        JSONObject pid = new JSONObject();

        pid.put("nom", pb);
        pid.put("description", description.getText().toString());

        String desc = description.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "problemes", pid, !true);

                    DescriptionProbleme.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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