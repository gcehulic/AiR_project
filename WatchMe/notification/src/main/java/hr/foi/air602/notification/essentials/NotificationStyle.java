package hr.foi.air602.notification.essentials;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Goran on 26.1.2017..
 */

/*
    Sučelje služi za definiranje ima li notifikacija zvuka i/ili vibraciju ili ništa od toga.
 */
public interface NotificationStyle {
    Notification getNotification(String title, String message, Context ctx);
}
