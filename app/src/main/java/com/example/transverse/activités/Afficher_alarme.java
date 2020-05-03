package com.example.transverse.activités;

import android.content.Intent;
import android.os.Bundle;

import com.example.transverse.R;
import com.example.transverse.activités.Alarm_set;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;


public class Afficher_alarme extends AppCompatActivity  {
    private static final int MY_REQUEST_CODE = 666; // sert de "code secret"

    private TextView boite1;
    private TextView boite2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_alarme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // bouton d'ajout des alarmes
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_alarm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlarm_set();
            }
        });

        boite1 = findViewById(R.id.boite);



        boite2 = findViewById(R.id.boite2);


    }

    // ouvre la page de réglage d'alarme
    public void openAlarm_set(){
        Intent intent = new Intent(this, Alarm_set.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }

}
