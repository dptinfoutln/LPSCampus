package com.univtln.univTlnLPS.ihm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.ihm.adapter.AdapterString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VoirRapportsBugs extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SSGBDControleur ssgbdControleur;

    ListView listeViewRapports;
    List<String> listeRapports, listeCat;
    private AdapterString listeAdapter;
    private Spinner spinnerCat;
    private TextView DateDeb, DateFin;

    private long idObj;
    private String date;

    private JSONObject jsonObj = null;
    private JSONArray jArray = null;

    private String cat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_rapports_bugs);


        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur) i.getSerializableExtra("ssgbdC");

        spinnerCat = findViewById(R.id.spinnerCat);
        DateDeb = findViewById(R.id.DateDeb);
        DateFin = findViewById(R.id.DateFin);


        affichageSpinner();
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

    public void affichageSpinner() {
        listeViewRapports = (ListView) findViewById(R.id.listeRapports);


        new Thread(new Runnable() {
            @Override
            public void run() {

                // On recupere la liste des categories
                listeCat = new ArrayList<>();

                try {
                    jArray = new JSONArray(ssgbdControleur.doRequest("GET", "cat", null, !true));

                    for (int i = 0; i < jArray.length(); i++)
                        listeCat.add(jArray.getString(i));

                    // on donne la liste des cat au spinner
                    VoirRapportsBugs.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(VoirRapportsBugs.this,
                                    android.R.layout.simple_spinner_item, listeCat);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCat.setAdapter(dataAdapter);

                            spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String resultat = (String) parent.getItemAtPosition(position);
                                    cat = resultat.split(":")[0];
                                    affichageListe();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                            if (jArray.length() > 0) {
                                try {
                                    cat = jArray.getString(0);
                                    affichageListe();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void affichageListe() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String param = "";

                    if (!"".equals(DateDeb.getText().toString()) && !"".equals(DateFin.getText().toString()))
                        param = "&debut=" + DateDeb.getText().toString() + "&fin=" + DateFin.getText().toString();

                    jsonObj = SSGBDControleur.getJSONFromJSONString(
                            ssgbdControleur.doRequest("GET", "bugReports?category=" + cat + param, null, !true));

                    listeRapports = new ArrayList<>();

                    // remplissage de la liste
                    Iterator<String> it2 = jsonObj.keys();
                    while (it2.hasNext()) {
                        String key = it2.next();
                        try {
                            listeRapports.add(key + ":" + new Date(((JSONObject) jsonObj.get(key)).getLong("date")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    listeAdapter = new AdapterString(VoirRapportsBugs.this, listeRapports);

                    VoirRapportsBugs.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listeViewRapports.setAdapter(listeAdapter);
                            listeViewRapports.setOnItemClickListener(VoirRapportsBugs.this);
                            listeViewRapports.refreshDrawableState();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void voir(View v) {
        Intent i = new Intent(this, AffichageDetailRapport.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        i.putExtra("idObj", idObj);
        startActivity(i);

    }

    public void suppr(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ssgbdControleur.doRequest("DELETE", "bugReports/" + idObj, null, !true);
                    affichageListe();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static Date txtViewToDate(TextView txt) {
        String[] str = txt.getText().toString().split("-");
        return Date.from(LocalDate.of(Integer.parseInt(str[2]),
                Integer.parseInt(str[1])-1,
                Integer.parseInt(str[0])).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void calendrier(TextView t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                String prevTxt = t.getText().toString();
                t.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                if (!DateDeb.getText().toString().equals("") && !DateFin.getText().toString().equals("")) {
                    Date deb = VoirRapportsBugs.txtViewToDate(DateDeb);
                    Date fin = VoirRapportsBugs.txtViewToDate(DateFin);

                    if (!fin.after(deb)) {
                        t.setText(prevTxt);
                        Toast.makeText(VoirRapportsBugs.this,
                                "La date de fin doit être après celle de début !",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        affichageListe();
                    }
                }
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
        datePickerDialog.show();
    }

    public void debut(View v) {
        calendrier(DateDeb);

    }

    public void fin(View v) {
        calendrier(DateFin);
    }

}