package hr.foi.air602.notification.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;

import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationOptions;

/**
 * Created by Goran on 18.1.2017..
 */

//Klasa kreira i pokazuje notifikacije
public class NotificationUtils {
    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;
    private static boolean sound = true;
    private static boolean vibration = false;

    public NotificationUtils(Context mContext){
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, Intent intent) {
        showNotificationMessage(title, message, intent, null);
    }

    //Kreira se akcija koja se pokreće nakon pritiska na notifikaciju
    public void showNotificationMessage(final String title, final String message, Intent intent, String imageUrl){
        if(TextUtils.isEmpty(message)) return;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        showSmallNotification(mBuilder,title, message,resultPendingIntent);
    }

    //gradi se cijeli notifikacija i prikazuje se
    private void showSmallNotification(NotificationCompat.Builder mBuilder, String title, String message, PendingIntent resultPendingIntent){
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        Notification notification;
        mBuilder.setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(inboxStyle)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);
        if(sound && vibration){
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        } else if(sound){
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        } else if(vibration){
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        }

        notification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    //briše sve notifikacije kada se orvori aplikacija
    public static void clearNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    //primjenjuje korisnički definirane postavke koje korisnik šalje modulu preko sučelja NotificationOptions
    public static void applyNotificationSettings(NotificationOptions notificationOptions){
        sound = notificationOptions.getSound();
        vibration = notificationOptions.getVibration();
    }
}
