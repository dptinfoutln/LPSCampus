package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AffichageSalles extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SSGBDControleur ssgbdControleur;
    private ListView ListeViewSalles;
    private List<String> ListeSalles;
    private AdapterString ListeAdapter;
    private Spinner spinnerUser;


    private String lastId, lastId2, nom, nom2, chaine, role;
    private List<String> list;
    private JSONObject jsonObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_salles);

        spinnerUser = findViewById(R.id.spinnerUser);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

        lastId = "-1";
        role = "SUPER";

        affichageSuper();
        affichageSalle();

    }

    /**
     * Listener OnClick pour la liste des salles
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String resultat = (String) parent.getItemAtPosition(position);
        lastId = resultat.split(":")[0];
        nom = resultat.split(":")[1];

        ListeAdapter.setItemSelected(position);
        ListeAdapter.notifyDataSetChanged();
    }

    public void affichageSuper() {
        ListeAdapter = new AdapterString(this, new ArrayList<>());
        ListeViewSalles = (ListView) findViewById(R.id.listedonneessalle);

        // r??cup??ration de la liste des users pour l'admin sinon du nom du superviseur
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    role = ssgbdControleur.doRequest("GET", "superviseurs/me/role", null, !true);
                    role = role.substring(0, role.length() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                chaine = null;

                list = new ArrayList<>();
                jsonObj = null;
                if (role.equals("ADMIN")) {
                    try {
                        jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "superviseurs", null, !true));

                        // remplissage de la liste
                        Iterator<String> it2 = jsonObj.keys();
                        while (it2.hasNext()) {
                            String key = it2.next();
                            list.add(key + ":" + ((JSONObject) jsonObj.get(key)).getString("email"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        jsonObj = SSGBDControleur.getJSONFromJSONString(ssgbdControleur.doRequest("GET", "superviseurs/me", null, !true));
                        list.add(jsonObj.get("id") + ":" + jsonObj.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



                // on donne la liste au spinner
                AffichageSalles.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AffichageSalles.this,
                                android.R.layout.simple_spinner_item, list);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerUser.setAdapter(dataAdapter);
                        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String resultat = (String) parent.getItemAtPosition(position);
                                lastId2 = resultat.split(":")[0];
                                nom2 = resultat.split(":")[1];

                                ListeAdapter.setItemSelected(position);
                                ListeAdapter.notifyDataSetChanged();

                                affichageSalle();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
            }
        }).start();
    }


    public void affichageSalle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ListeSalles = new ArrayList<>();

                // ?? reprendre : affichage de la liste des pi??ces selon l'item s??lectionn?? du spinner
                try {
                    // ?? changer pour superviseur et admin et rafraichir
                    if (role.equals("SUPER")) {
                        chaine = ssgbdControleur.doRequest("GET", "superviseurs/me/scans/pieces", null, !true);
                        ListeViewSalles.refreshDrawableState();
                    }
                    else if (role.equals("ADMIN")) {
                        // changer le lastId par la nouvelle variable associ??e au nouveau onItemClick
                        chaine = ssgbdControleur.doRequest("GET", "superviseurs/" + lastId2 + "/scans/pieces", null, !true);
                        ListeViewSalles.refreshDrawableState();
                    }


                    JSONObject jchaine = SSGBDControleur.getJSONFromJSONString(chaine);

                    Iterator<String> it = jchaine.keys();
                    String name;
                    while (it.hasNext()) {
                        name = it.next(); // id de l'objet piece
                        name += ":" + ((JSONObject)jchaine.get(name)).getString("name"); // on r??cup??re le nom associ??
                        ListeSalles.add(name);
                    }
                    ListeAdapter = new AdapterString(AffichageSalles.this, ListeSalles);

                    AffichageSalles.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListeViewSalles.setAdapter(ListeAdapter);
                            ListeViewSalles.setOnItemClickListener(AffichageSalles.this);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onClickChoisir(View v) {
        if (!"-1".equals(lastId)) {
            Intent i = new Intent(this, AffichageDonneesSalle.class);
            i.putExtra("ssgbdC", ssgbdControleur);
            i.putExtra("salleId", lastId);
            i.putExtra("piece", nom);
            i.putExtra("role", role);
            i.putExtra("id", lastId2);
            startActivity(i);
        }
    }
}