package com.example.transverse.activités;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.transverse.R;
import com.example.transverse.autres.Alarm_set;
import com.example.transverse.autres.AlertReceiver;
import com.example.transverse.autres.InstructionDialog;
import com.example.transverse.autres.TimePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Afficher_alarme extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final int MY_REQUEST_CODE = 666; // sert de "code secret"

    private EditText input_pill;
    ArrayList <String> liste_alarme = new ArrayList<>();
    ArrayList <Calendar> dates_alarmes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_alarme);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        openInstruction();

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

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //nom = findViewById(R.id.nom);





    }


    // Ouvre "l'horloge" du téléphone pour régler l'alarme
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        liste_alarme.add(updateTimeText(c));
        dates_alarmes.add(c);
        startAlarm(c); // programme la notif pour l'heure choisi


        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, liste_alarme);
        final ListView listView = findViewById(R.id.liste_alarmes);
        listView.setAdapter(adapter);
        Toast.makeText(this,"" + liste_alarme, Toast.LENGTH_LONG).show();


        // tester  avec longclick pour suppr
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray positionchecker = listView.getCheckedItemPositions();

                for (int i = listView.getCount(); i >=0 ; i --){
                    if (positionchecker.get(i)){

                        // non fonctionnel à 100% : supprime TOUTES les alarmes 
                        //cancelAlarm(dates_alarmes.get(i));
                        adapter.remove(liste_alarme.get(i));
                    }
                }

                positionchecker.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });
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
    private  void cancelAlarm(Calendar dates_alarmes){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);

    }



    // popup pour informer sur la suppression d'alarme (cf InstructionDialog.java)
    public void openInstruction() {
        InstructionDialog exampleDialog = new InstructionDialog();
        exampleDialog.show(getSupportFragmentManager(), "");
    }


}
