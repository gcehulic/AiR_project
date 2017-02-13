package hr.foi.air602.notification.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import org.joda.time.DateTime;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationListener;
import hr.foi.air602.notification.essentials.NotificationStyle;
import hr.foi.air602.notification.notification_options.SoundNotification;
import hr.foi.air602.notification.notification_options.VibrationNotification;
import hr.foi.air602.notification.service.NotificationPublisher;

/**
 * Created by Goran on 18.1.2017..
 */

//Klasa kreira i pokazuje notifikacije
public class NotificationUtils  implements NotificationListener{
    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;
    private static boolean sound = true;
    private static boolean vibration = false;
    private NotificationStyle notification = new SoundNotification();

    private static NotificationUtils INSTANCE = null;

    private NotificationUtils(Context mContext){
        this.mContext = mContext;
    }

    public static NotificationUtils getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new NotificationUtils(context);
        }
        return INSTANCE;
    }

    public NotificationListener getListener(){
        return this;
    }

    public void scheduleNotification(Notification notification, DateTime time, int notificationId){

        Log.e(TAG, "scheduleNotification: time: " + time + " id: " + notificationId );
        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("notification-id",notificationId);
        notificationIntent.putExtra("notification",notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        Log.e(TAG, "scheduleNotification: " + System.currentTimeMillis() + " " + time.getMillis() );

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time.getMillis(),AlarmManager.INTERVAL_DAY * 7,pendingIntent);
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




    }


    //briše sve notifikacije kada se orvori aplikacija
    public static void clearNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @Override
    public void onNotificationSchedule(String title, String content, DateTime dt, int notificationID) {
        this.scheduleNotification(this.notification.getNotification(title, content, mContext), dt, notificationID);
    }

    @Override
    public void onNotificationCancel(int notificationID) {
        Intent intent = new Intent(mContext, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    @Override
    public void onNotificationSettingsChange(int flag) {
        if(flag == 1) this.notification = new SoundNotification();
        else this.notification = new VibrationNotification();
    }
}
