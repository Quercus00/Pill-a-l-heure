package com.example.transverse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;


// Classe qui envoie la notif sur le tel
public class AlertReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Medishare","Dingding it's time !"); // bug car manque le code de NotificationHelper
        notificationHelper.getManager().notify(1, nb.build());
    }
}
