package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class DevenirSuperviseur extends AppCompatActivity {

    private SSGBDControleur ssgbdControleur;
    EditText loginTxt, mdpTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devenir_superviseur);

        loginTxt = findViewById(R.id.loginTxt);
        mdpTxt = findViewById(R.id.mdpTxt);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");

    }

    public void checkForm(View v){
        String login = loginTxt.getText().toString();
        String mdp = mdpTxt.getText().toString();

        // Verifions que le mail ait ete renseigne
        if (! login.contains("@") && (! login.contains("univ-tln.fr"))){
            // Affichage d'un probleme et l'utilisateur reste sur la page
            Toast.makeText(this, "Adresse email non reconnue", Toast.LENGTH_LONG).show();
            return;
        }
        // Verifions que le mot de passe a plus de 5 caracteres
        if(mdp.length() > 4){
            // Affichage temporaire success
            Toast.makeText(this, "Demande envoyée", Toast.LENGTH_LONG).show();

            // Retour a la page principale
            finish();
        }

        else{
            // Affichage temporaire mot de passe incorrect
            Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_LONG).show();
        }
    }
}