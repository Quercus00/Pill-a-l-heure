package com.example.transverse.autres;

//---------------------------------------------------------------------------------
//--------------Activité plus utilisée, tout est fusionné avec afficher_alarme-----
//---------------------------------------------------------------------------------

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.transverse.activités.Accueil;
import com.example.transverse.autres.Alarm;
import com.example.transverse.autres.AlertReceiver;
import com.example.transverse.R;
import com.example.transverse.autres.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class Alarm_set extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private TextView mTextView;
    private EditText input_pill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);

        mTextView = findViewById(R.id.textView);
        input_pill = findViewById(R.id.input_pill);

        // code appel fct bouton horloge (appel class TimePickerFragment)
        Button buttonTimePicker = findViewById(R.id.btn_time_picker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        // code appel fct bouton cancel
        Button buttonCancelAlarm = findViewById(R.id.btn_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        // code appel fonction bouton retour
        Button btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotta_go_back();
            }
        });

    }

    // Code pour retourner sur une page précédente
    public void gotta_go_back(){
        Intent intent = new Intent(this, Accueil.class);
        startActivity(intent);
    }

    // Ouvre "l'horloge" du téléphone pour régler l'alarme
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    // code qui s'exécute lorsque l'alarme a été réglée. Met à jour le texte basique
    private Alarm updateTimeText(Calendar c){
        String timeText = input_pill.getText().toString() + " réglé pour : ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        mTextView.setText(timeText);

        //création d'un objet "alarme" pour l'afficher dans la page Afficher alarme
        // j'ai modifié le type de la méthode pour pourvoir retourner l'obejet, mais à la base c'est un void


        //re modifié, il faut remettre les parametres de new alarm ( c'etait pour un test de afficher alarme)
        Alarm alarme = new Alarm();
        return alarme;
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
        mTextView.setText("Traitement annulé !");
    }
}
