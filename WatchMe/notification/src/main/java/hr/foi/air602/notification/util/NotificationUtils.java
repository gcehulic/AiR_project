package hr.foi.air602.notification.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationOptions;
import hr.foi.air602.notification.notification_options.SilentNotification;
import hr.foi.air602.notification.notification_options.SoundNotification;
import hr.foi.air602.notification.notification_options.SoundVibrationNotification;
import hr.foi.air602.notification.notification_options.VibrationNotification;

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

        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(Config.SHARED_PREF_OPTIONS, Context.MODE_PRIVATE);
        sound = sharedPreferences.getBoolean("sound", true);
        vibration = sharedPreferences.getBoolean("vibration", false);

        NotificationOptions notification = null;
        if(sound && vibration) notification = new SoundVibrationNotification();
        else if(sound) notification = new SoundNotification();
        else if(vibration) notification = new VibrationNotification();
        else notification = new SilentNotification();
        notification.showNotification(mBuilder,title, message, resultPendingIntent, mContext);
    }

    //briše sve notifikacije kada se orvori aplikacija
    public static void clearNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
