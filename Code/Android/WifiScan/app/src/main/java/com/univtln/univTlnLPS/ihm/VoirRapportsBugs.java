package com.univtln.univTlnLPS.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VoirRapportsBugs extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SSGBDControleur ssgbdControleur;

    ListView listeViewRapports;
    List<String> listeRapports, listeCat;
    private AdapterString listeAdapter;
    private Spinner spinnerCat;

    private long idObj;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_rapports_bugs);


        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur) i.getSerializableExtra("ssgbdC");

        affichageRapports();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String resultat = (String) parent.getItemAtPosition(position);
        idObj = Integer.valueOf( resultat.split(":")[0]);
        date = resultat.split(":")[1];

        listeAdapter.setItemSelected(position);
        listeAdapter.notifyDataSetChanged();
    }


    public void affichageRapports() {
        listeAdapter = new AdapterString(this, new ArrayList<>());
        listeViewRapports = (ListView) findViewById(R.id.listeRapports);
        listeRapports = new ArrayList<>();


        new Thread(new Runnable() {
            @Override
            public void run() {

                // On recupere la liste des categories
                listeCat = new ArrayList<String>();
                listeCat.add("Mauvaise Localisation");
                listeCat.add("Mauvais Plan");
                listeCat.add("Dysfonctionnement");


                // on donne la liste des cat au spinner
                VoirRapportsBugs.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(VoirRapportsBugs.this,
                                android.R.layout.simple_spinner_item, listeCat);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCat.setAdapter(dataAdapter);
                    }
                });


                // à reprendre : affichage de la liste des rapports selon les dates selected
                JSONObject jsonObj = null;

                try {
                    jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "bugReports", null, !true));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // remplissage de la liste
                Iterator<String> it2 = jsonObj.keys();
                while (it2.hasNext()) {
                    String key = it2.next();
                    try {
                        listeRapports.add(key + ":" + ((JSONObject) jsonObj.get(key)).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                VoirRapportsBugs.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listeViewRapports.setAdapter(listeAdapter);
                        listeViewRapports.setOnItemClickListener(VoirRapportsBugs.this);
                    }
                });
            }
        }).start();
    }

    public void onClickChoisir(View v) {
        Intent i = new Intent(this, AffichageRapport.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        i.putExtra("idRep", idObj);
        i.putExtra("date", date);
        startActivity(i);
    }

}