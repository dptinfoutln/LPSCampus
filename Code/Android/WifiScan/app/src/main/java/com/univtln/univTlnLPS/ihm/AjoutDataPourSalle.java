package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.Position;
import com.univtln.univTlnLPS.client.SSGBDControleur;
import com.univtln.univTlnLPS.common.WriteFile;
import com.univtln.univTlnLPS.scan.ScanListAdapter;
import com.univtln.univTlnLPS.scan.WifiScan;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjoutDataPourSalle extends AppCompatActivity implements Runnable {

    private SSGBDControleur ssgbdControleur;
    private WifiScan wifiScan;
    private ScanListAdapter adapter;

    private EditText editTxt, editTxtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_data_pour_salle);

        wifiScan = new WifiScan(getApplicationContext());

        ListView lv = findViewById(R.id.scanList);
        adapter = new ScanListAdapter(this, new ArrayList<>());
        lv.setAdapter(adapter);

        editTxt = findViewById(R.id.infos);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");


    }

    Button btn;
    public void launch(View v){
        /** /!\ ATTENTION: Ne pas appeler wifiScan.startScan() dans un thread,
         *  à cause des limitations de scan.
         **/
        btn = (Button)v;

        btn.setEnabled(false);
        boolean success = wifiScan.startScan();
        if(success)
            new Thread(this).start();
        else
            error();

    }

    public void error(){
        if (btn != null) {
            Toast.makeText(AjoutDataPourSalle.this, "An error occured! Please retry! Activate Localisation!", Toast.LENGTH_LONG).show();
            btn.setEnabled(true);
        }
    }


    @Override
    public void run() {
        while(!wifiScan.isNewScanResultsAvailable()){
            try {
                Thread.sleep(300);
                if(wifiScan.hasFailed()) {
                    AjoutDataPourSalle.this.runOnUiThread(new Runnable() {
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

        adapter.setScanList(scanResults);

        String salle = editTxt.getText().toString();
        if(!"".equals(salle)){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String currentDateandTime = sdf.format(new Date());

                String infoScan = editTxtInfo.getText().toString();
                String directory = "scanData";
                String fileName = salle + ".txt";

                StringBuilder data = new StringBuilder();
                data.append("scan:").append(currentDateandTime).append("\n");
                data.append("infos:").append(infoScan).append("\n");
                for(ScanResult scanRes : scanResults){
                    data.append("BSSID:").append(scanRes.BSSID).append("\n");
                    data.append("Capabilities:").append(scanRes.capabilities).append("\n");
                    data.append("CenterFreq0:").append(scanRes.centerFreq0).append("\n");
                    data.append("CenterFreq1:").append(scanRes.centerFreq1).append("\n");
                    data.append("ChannelWidth:").append(scanRes.channelWidth).append("\n");
                    data.append("Frequency:").append(scanRes.frequency).append("\n");
                    data.append("Level:").append(scanRes.level).append("\n");
                    data.append("OperatorFriendlyName:").append(scanRes.operatorFriendlyName).append("\n");
                    data.append("SSID:").append(scanRes.SSID).append("\n");
                    data.append("Timestamp:").append(scanRes.timestamp).append("\n");
                    data.append("VenueName:").append(scanRes.venueName).append("\n");
                    data.append("\n");
                }
                data.append('\n');

                WriteFile.createFile(directory, fileName);
                WriteFile.writeToFile(data.toString(), directory, fileName, 'A');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject res = null;
        String mess = "";
        try {
            res = Position.convertScan(wifiScan.getResults());
            mess = res.toString();
            Position.get(Position.uri1 + ssgbdControleur.getIp() + Position.uri2, res);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //source: https://stackoverflow.com/questions/8665676/how-to-use-notifydatasetchanged-in-thread
        AjoutDataPourSalle.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                if (scanResults.size() == 0){
                    Toast.makeText(AjoutDataPourSalle.this, "Activate Localisation", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AjoutDataPourSalle.this, "success", Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, "Scan success", Toast.LENGTH_LONG).show();
                }

                btn.setEnabled(true);
            }
        });
    }
}