package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

import org.json.JSONException;

public class BDD extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_d);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void supprimerCompteSuper(View v) {
        Intent i = new Intent(this, SupprimerCompteSuperviseur.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerCampus(View v) {
        Intent i = new Intent(this, SupprimerCampus.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerBatiment(View v) {
        Intent i = new Intent(this, SupprimerBatiment.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerEtage(View v) {
        Intent i = new Intent(this, SupprimerEtage.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void supprimerSalle(View v) {
        Intent i = new Intent(this, SupprimerSalle.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        startActivity(i);

    }

    public void flush(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("/!\\ ATTENTION /!\\");
        builder.setMessage("Voulez-vous vraiment REINITIALISER la BDD");
        builder.setPositiveButton("Confirmer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ssgbdControleur.doRequest("PUT", "flush", null, true);
                                    BDD.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(BDD.this,
                                                    "La base de données à bien été réinitialisée",
                                                    Toast.LENGTH_LONG).show();
                                            BDD.this.finish();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}