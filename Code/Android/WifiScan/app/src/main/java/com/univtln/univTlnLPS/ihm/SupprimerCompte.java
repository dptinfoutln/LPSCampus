package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class SupprimerCompte extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    private String id;
    private CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_compte);
        check = findViewById(R.id.checkboxValider);
    }

    public void valider(View v) {
        // definir l'id ici
        if (check.isChecked()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ssgbdControleur.doRequest("DELETE", "superviseurs/" + id, null, !true);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            }).start();
        }
    }

}