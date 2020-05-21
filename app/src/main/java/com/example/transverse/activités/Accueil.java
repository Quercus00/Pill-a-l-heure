package com.example.transverse.activités;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.transverse.R;


public class Accueil extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 666;

    Button btn_sign_out;
    Button btn_alarm_list;
    Button btn_profil;
    Button btn_amis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btn_sign_out = findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotta_go_back();
            }
        });

        try {
            this.getSupportActionBar().setTitle("MediShare - Accueil");
        }
        catch (NullPointerException e){}


        //go page liste alarmes
        btn_alarm_list = findViewById(R.id.btn_liste_alarm);
        btn_alarm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarm_list();
            }
        });


        btn_profil = findViewById(R.id.btn_profil);
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_profil();
            }
        });

        btn_amis = findViewById(R.id.btn_amis);
        btn_amis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_amis();

            }
        });
    }

    //code pour retourner sur une page précédente
    public void gotta_go_back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void open_profil(){
        Intent intent = new Intent(this, activity_profil.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }

    public void openAlarm_list(){
        Intent intent = new Intent(this, Afficher_alarme.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }

    public void open_amis(){
        Intent intent = new Intent(this, Activity_amis.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }


}
