package hr.foi.air602.notification.essentials;

import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Goran on 26.1.2017..
 */

/*
    Sučelje služi za definiranje ima li notifikacija zvuka i/ili vibraciju ili ništa od toga.
 */
public interface NotificationOptions {
    void showNotification(NotificationCompat.Builder mBuilder, String title, String message, PendingIntent resultPendingIntent, Context ctx);
}
