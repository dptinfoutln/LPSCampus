package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.carte.model.Batiment;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreerEtage extends AppCompatActivity {


    private EditText nomEt, planBat;
    private Spinner batiments;
    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_etage);
        nomEt = findViewById(R.id.nomEtage);
        planBat = findViewById(R.id.plan);

        batiments = findViewById(R.id.spinnerBatiments);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        getEtages();

    }


    public void getEtages() {
        List<String> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "batiments", null, !true));

                    Iterator<String> it = jsonObj.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        list.add( key + ":" + ((JSONObject)jsonObj.get(key)).getString("name") );
                    }


                    CreerEtage.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreerEtage.this,
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            batiments.setAdapter(dataAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void createEtage(View v) throws JSONException {
        String nom = nomEt.getText().toString();
        JSONObject eid = new JSONObject();
        Batiment bat = null;
        if (batiments.getSelectedItem() != null) {
            String batiment = (String) batiments.getSelectedItem();
            String[] str = batiment.split(":");
            if (str.length == 2)
                bat = new Batiment(Long.parseLong(str[0]), str[1]);
        }

        eid.put("name", nom);
        eid.put("bat", bat);
        eid.put("plan", planBat.getText().toString());
        eid.put("id", -1);
        eid.put("pieceList", null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "etages", eid, !true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void createBatiment(View v) {
        Intent i = new Intent(this, CreerBatiment.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

}