package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

public class AffichageDetailRapport extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private TextView aff;
    private long id;
    private JSONObject j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_rapport);

        aff = findViewById(R.id.affichageDetail);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        id = i.getLongExtra("idObj", 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    j = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "bugReports/" + id, null, !true));
                    AffichageDetailRapport.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                aff.setText(j.getString("content"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}