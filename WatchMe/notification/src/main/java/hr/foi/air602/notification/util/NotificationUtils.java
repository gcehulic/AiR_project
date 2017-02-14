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

public class NotificationUtils  implements NotificationListener{
    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;
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
        notificationIntent.putExtra("notification-time",time.toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        time = time.minusMinutes(NotificationPublisher.NOTIFY_BEFORE_MINUTES);

        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "scheduleNotification: norification set at: " + time.toString());
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time.getMillis(),pendingIntent);

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

    @Override
    public void onNotifyMinutesBeforeShow(int minutes) {
        NotificationPublisher.NOTIFY_BEFORE_MINUTES = minutes;
    }


}
