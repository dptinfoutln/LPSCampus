package com.univtln.univTlnLPS.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;

import java.util.Arrays;

public class Permissions extends AppCompatActivity {

    private final int MY_PERM_STORAGE_W = 101;
    private final int MY_PERM_LOCALISATION_C = 102;
    private final int MY_PERM_LOCALISATION_F = 103;

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        txtView = (TextView)findViewById(R.id.txtperm);
        acceptPerm(null);
    }

    public void acceptPerm(View v) {
        if (checkPerm(Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERM_STORAGE_W))
            finish();
    }

    public static boolean isPermGranted(Context c) {
        return (ContextCompat.checkSelfPermission(c,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(c,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(c,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
            //all permissions have been granted

            if (requestCode == MY_PERM_STORAGE_W) {
                checkPerm(Manifest.permission.ACCESS_FINE_LOCATION, MY_PERM_LOCALISATION_F);
            }
            else if (requestCode == MY_PERM_LOCALISATION_F) {
                if (checkPerm(Manifest.permission.ACCESS_COARSE_LOCATION, MY_PERM_LOCALISATION_C)) {
                    requestCode = MY_PERM_LOCALISATION_C;
                }
            }
            if (requestCode == MY_PERM_LOCALISATION_C) {
                Toast.makeText(this, "Permissions acceptées", Toast.LENGTH_LONG).show();
                txtView.setText("Permissions accordées");
                Permissions.this.finish();
            }
        }
        else {
            Toast.makeText(this, "Permissions refusées", Toast.LENGTH_LONG).show();
            txtView.setText("Permissions refusées :(");
        }

    }

    private boolean checkPerm(String manifestPerm, int myPerm) {
        if (ContextCompat.checkSelfPermission(this,
                manifestPerm)
                != PackageManager.PERMISSION_GRANTED)  {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    manifestPerm)) {
                txtView.setText("Afin de pouvoir scanner et garder en mémoire les scans, relancer l'application" +
                        "puis accepter ou bien manuellement dans les paramètres.");
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{manifestPerm},
                        myPerm);

                if (ContextCompat.checkSelfPermission(this,
                        manifestPerm)
                        != PackageManager.PERMISSION_GRANTED){
                    txtView.setText("Permissions refusées :(");
                }
                else
                    txtView.setText("Veuillez ajouter le nouveau widget sur votre écran d'acceuil");

            }
        } else {
            txtView.setText("Permissions accordées");
            return true;
        }
        return false;
    }
}
