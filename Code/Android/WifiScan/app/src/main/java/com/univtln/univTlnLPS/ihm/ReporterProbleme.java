package com.univtln.univTlnLPS.ihm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.univtln.univTlnLPS.R;
import com.univtln.univTlnLPS.client.SSGBDControleur;

public class ReporterProbleme extends AppCompatActivity {

    private RadioGroup radioGroup;
    private SSGBDControleur ssgbdControleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_probleme);

        radioGroup = findViewById(R.id.radiogroup);

        Intent i = getIntent();
        ssgbdControleur = (SSGBDControleur)i.getSerializableExtra("ssgbdC");
    }

    public void valider(View v) {
        String radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        Intent i = new Intent(this, DescriptionProbleme.class);
        i.putExtra("ssgbdC", ssgbdControleur);
        i.putExtra("radioGroupId", radiovalue);
        startActivity(i);

    }
}