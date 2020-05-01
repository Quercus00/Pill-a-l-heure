package com.example.transverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class Accueil extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 666;
// test
    Button btn_sign_out;
    Button btn_alarm;
    Button btn_alarm_list;

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

        //go page ajouter alarme
        btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarm_set();
            }
        });


        //go page liste alarmes
        btn_alarm_list = findViewById(R.id.btn_liste_alarm);
        btn_alarm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlarm_list();
            }
        });


        TextView txt_info = findViewById(R.id.txt_info);
        TextView txt_mail = findViewById(R.id.txt_mail);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {

                // Id of the provider (ex: google.com)
                //String providerId = profile.getProviderId();

                // UID specific to the provider
                //String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();

                txt_info.setText(name);
                txt_mail.setText(email);
            }
        }
    }

    //code pour retourner sur une page précédente
    public void gotta_go_back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAlarm_set(){
        Intent intent = new Intent(this, Alarm_set.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }

    public void openAlarm_list(){
        Intent intent = new Intent(this, Afficher_alarme.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }
}
