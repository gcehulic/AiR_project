package hr.foi.air602.notification.notification_options;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationStyle;

/**
 * Created by Goran on 26.1.2017..
 */


public class VibrationNotification implements NotificationStyle {
    @Override
    public Notification getNotification(String title, String message, Context ctx) {
        Notification.Builder builder = new Notification.Builder(ctx);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        return builder.build();
    }
}
