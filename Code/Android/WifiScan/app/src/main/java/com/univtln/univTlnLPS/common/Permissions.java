package com.univtln.univTlnLPS.common;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;

import com.univtln.univTlnLPS.R;

public class Permissions extends AppCompatActivity {

    private final int MY_PERM_STORAGE_W = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions);

        TextView txtView = (TextView)findViewById(R.id.txtperm);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)  {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                txtView.setText("Afin de pouvoir garder en mémoire les scans, relancer l'application" +
                        "puis accepter ou bien manuellement dans les parametres.");
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERM_STORAGE_W);

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                    txtView.setText("Permissions refusées :(");
                }
                else
                    txtView.setText("Veuiller ajouter le nouveau widget sur votre ecran d'acceuil");

            }
        } else {
            txtView.setText("Permissions accordés");
        }
    }
}
