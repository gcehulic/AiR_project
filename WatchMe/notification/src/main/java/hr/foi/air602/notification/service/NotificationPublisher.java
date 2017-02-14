package hr.foi.air602.notification.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.joda.time.DateTime;

import hr.foi.air602.notification.util.NotificationUtils;

/**
 * Created by Goran on 10.2.2017..
 */

public class NotificationPublisher extends BroadcastReceiver {
    public static int NOTIFY_BEFORE_MINUTES = 0;
    private int REPEATING_TIME =  7 * 60 * 24; //7 dana
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra("notification");
        int id = intent.getIntExtra("notification-id", 0);
        String timeString = intent.getStringExtra("notification-time");
        notificationManager.notify(id, notification);
        Toast.makeText(context, "Notification arrived", Toast.LENGTH_LONG).show();
        Log.e("notificationpublisher", "onReceive: " + notification.extras);

        DateTime time = new DateTime(timeString);
        time = time.plusMinutes(REPEATING_TIME);

        Log.e("notificationpublisher", "onReceive: new time : " + time.toString());
        Log.e("notificationpublisher", "onReceive: repeat minutes:  " + REPEATING_TIME);
        String title = notification.extras.getString("android.title");
        String content = notification.extras.getString("android.text");
        NotificationUtils.getInstance(context).onNotificationSchedule(title, content,time, id);
    }
}

