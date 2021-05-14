package com.univtln.univTlnLPS.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.carte.model.Etage;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreerSalle extends AppCompatActivity {

    private EditText creerSalle;
    private EditText position_x;
    private EditText position_y;
    private Spinner etages;

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_salle);
        creerSalle = findViewById(R.id.creerSalle);
        position_x = findViewById(R.id.position_x);
        position_y = findViewById(R.id.position_y);
        etages = findViewById(R.id.spinnerEtages);

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
                    JSONObject jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "etages", null, !true));

                    Iterator<String> it = jsonObj.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        list.add( key + ":" + ((JSONObject)jsonObj.get(key)).getString("name") );
                    }


                    CreerSalle.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(CreerSalle.this,
                                    android.R.layout.simple_spinner_item, list);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            etages.setAdapter(dataAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void createSalle(View v) throws JSONException {
        String salle, etage;
        Etage et = null;
        int x, y;
        JSONObject pid = new JSONObject();
        JSONObject etageId = new JSONObject();

        // récupérer le nom, la position en x et y et l'étage
        salle = creerSalle.getText().toString();
        x = Integer.parseInt(position_x.getText().toString());
        y = Integer.parseInt(position_y.getText().toString());

        if (etages.getSelectedItem() != null) {
            etage = (String) etages.getSelectedItem();
            String[] str = etage.split(":");
            if (str.length == 2)
                et = new Etage(str[1], Long.parseLong(str[0]));
        }


        // vérifier que la salle n'existe pas
        // ajouter le nom de la salle entrée dans la base de données

        pid.put("position_x", x);
        pid.put("position_y", y);
        pid.put("name", salle);
        pid.put("id", 0);
        pid.put("etage", et);
        pid.put("scanList", null);

        String item = (String)etages.getSelectedItem();
        if (item == null)
            return;

        etageId.put("id", (item.split(":")[0]));

        pid.put("etage", etageId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("PUT", "pieces", pid, !true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }).start();
    }

    public void createEtage(View v) throws JSONException {
        Intent i = new Intent(this, CreerEtage.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }


}