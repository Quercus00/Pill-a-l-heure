package com.example.transverse.autres;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;

import com.example.transverse.R;


// Classe qui envoie la notif sur le tel

public class AlertReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Medishare","Il est l'heure de prendre votre médicament !");
        notificationHelper.getManager().notify(1, nb.build());
    }
}
