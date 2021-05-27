package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.Position;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.scan.WifiScan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SeLocaliser extends AppCompatActivity implements Runnable{

    private SSGBDControleur ssgbdControleur;

    private WifiScan wifiScan;
    private Button btn;
    private TextView tv;
    //private RadioButton texte, graphique;

    String image = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_localiser);

        tv = findViewById(R.id.connexion);
        //texte = findViewById(R.id.texte);
        //graphique = findViewById(R.id.graphique);

        wifiScan = new WifiScan(this);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void seLocaliser(View v){
        btn = (Button)v;

        btn.setEnabled(false);

        boolean success =  wifiScan.startScan();
        if(success)
            new Thread(this).start();
        else
            error();

    }

    public void error(){
        if (btn != null) {
            Toast.makeText(SeLocaliser.this, "An error occured! Please retry!", Toast.LENGTH_LONG).show();
            btn.setEnabled(true);
        }
    }

    @Override
    public void run() {
        while(!wifiScan.isNewScanResultsAvailable()){
            try {
                Thread.sleep(300);
                if(wifiScan.hasFailed()) {
                    SeLocaliser.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            error();
                        }
                    });
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        List<ScanResult> scanResults = wifiScan.getResults();

        JSONObject res = null;
        String position = "";
        try {
            res = Position.convertScan(wifiScan.getResults());
            position = Position.get(Position.uri1 + ssgbdControleur.getIp() + Position.uri2, res);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //source: https://stackoverflow.com/questions/8665676/how-to-use-notifydatasetchanged-in-thread
        String finalPosition = position;
        SeLocaliser.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // on affiche du texte si le radiobutton texte est sélectionné
                //if (texte.isChecked()) {
                    tv.setText("Vous êtes en "+ finalPosition);
                /*}
                // sinon on affiche un plan de l'étage
                else if (graphique.isChecked()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // afficher l'image correspondant au plan de l'étage
                                // image = ssgbdControleur.doRequest("GET", "plans" + id, null, !true);;
                                // tv.setCompoundDrawableWithIntrinsicBounds(R.drawable.image, 0, 0, 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }*/
                if (scanResults.size() == 0){
                    Toast.makeText(SeLocaliser.this, "An error occured! Please retry!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SeLocaliser.this, "success", Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, "Scan success", Toast.LENGTH_LONG).show();
                }
                btn.setEnabled(true);
            }
        });
    }


    public void probleme(View v) {
        Intent i = new Intent(this, ReporterProbleme.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);
    }
}