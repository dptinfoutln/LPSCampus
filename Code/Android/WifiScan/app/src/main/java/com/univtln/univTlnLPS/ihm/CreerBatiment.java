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
import com.univtln.univTlnLPS.carte.model.Campus;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreerBatiment extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    private EditText nomBat, pos_x, pos_y;
    private Spinner campus;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_batiment);

        nomBat = findViewById(R.id.nomBatiment);
        pos_x = findViewById(R.id.position_x);
        pos_y = findViewById(R.id.position_y);

        campus = findViewById(R.id.spinnerCampus);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        getBatiments();

    }


    public void getBatiments() {
        list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "campus", null, !true));

                    Iterator<String> it = jsonObj.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        list.add( key + ":" + ((JSONObject)jsonObj.get(key)).getString("name") );
                    }

                    CreerBatiment.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreerBatiment.this,
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            campus.setAdapter(dataAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void createBatiment(View v) throws JSONException {
        String nom;
        int x, y;

        nom = nomBat.getText().toString();
        x = Integer.parseInt(pos_x.getText().toString());
        y = Integer.parseInt(pos_y.getText().toString());

        JSONObject bid = new JSONObject();

        Campus camp = null;
        if (campus.getSelectedItem() != null) {
            String campusName = (String) campus.getSelectedItem();
            String[] str = campusName.split(":");
            if (str.length == 2)
                camp = new Campus(Long.parseLong(str[0]), str[1]);
        }

        bid.put("name", nom);
        bid.put("listEtages", null);
        bid.put("position_x", x);
        bid.put("position_y", y);
        bid.put("id", 0);

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

    public void createCampus(View v) {
        Intent i = new Intent(this, CreerCampus.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }

}