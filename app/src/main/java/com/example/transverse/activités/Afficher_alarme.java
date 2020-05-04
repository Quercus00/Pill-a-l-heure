package com.example.transverse.activités;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.transverse.R;
import com.example.transverse.autres.AlertReceiver;
import com.example.transverse.autres.TimePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.DateFormat;
import java.util.Calendar;


public class Afficher_alarme extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final int MY_REQUEST_CODE = 666; // sert de "code secret"

    private TextView nom;
    private TextView boite2;
    private EditText input_pill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_alarme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // permet d'enlever le gros titre moche police 72 de l'activité
        try {
            this.getSupportActionBar().setTitle("");
        }
        catch (NullPointerException e){}

        input_pill = findViewById(R.id.input_pill);

        // bouton d'ajout des alarmes
        FloatingActionButton fab = findViewById(R.id.add_alarm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openAlarm_set();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        nom = findViewById(R.id.nom);
        Calendar calendar = Calendar.getInstance();




    }


    // Ouvre "l'horloge" du téléphone pour régler l'alarme
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        String alarme = updateTimeText(c);
        startAlarm(c);

        nom.setText(alarme);


    }

    // code qui s'exécute lorsque l'alarme a été réglée. Met à jour le texte basique
    private String updateTimeText(Calendar c){
        String timeText = input_pill.getText().toString() + " réglé pour : ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        return timeText;
    }

    //Lance l'alarme pour la date réglée
    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }


    // Annule l'arlame réglée et réinitialise la zone texte
    private  void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        nom.setText("Traitement annulé !");
    }







    // ouvre la page de réglage d'alarme
    public void openAlarm_set(){
        Intent intent = new Intent(this, Alarm_set.class);
        startActivity(intent);
        finishActivity(MY_REQUEST_CODE);
    }

}
