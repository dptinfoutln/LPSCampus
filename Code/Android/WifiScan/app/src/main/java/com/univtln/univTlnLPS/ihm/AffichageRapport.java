package com.univtln.univTlnLPS.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class AffichageRapport extends AppCompatActivity {
    private SSGBDControleur ssgbdControleur;
    private long idRep;
    private String date;
    private String content;
    private TextView dateTV, contentTV;
    private Boolean deleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_detail_rapport);

        dateTV = findViewById(R.id.dateTV);
        contentTV = findViewById(R.id.contentTV);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        idRep = i.getLongExtra("idRep", -1);
        date = i.getStringExtra("date");

        try {
            affichage();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void affichage() throws JSONException {

        dateTV.setText(date);

        //get report then set content
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    content = ssgbdControleur.doRequest("GET", "bugReports/" + idRep, null, !true);
                    AffichageRapport.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contentTV.setText(content);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void effacer(View v) throws JSONException {
        deleted = false;

        //delete report
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("DELETE", "bugReports/" + idRep, null, !true);
                    deleted = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if (deleted)
            Toast.makeText(this, "Rapport supprimé", Toast.LENGTH_LONG).show();
    }

}
