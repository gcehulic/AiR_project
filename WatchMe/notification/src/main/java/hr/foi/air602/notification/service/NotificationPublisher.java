package hr.foi.air602.notification.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Goran on 10.2.2017..
 */

public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra("notification");
        int id = intent.getIntExtra("notification-id", 0);
        notificationManager.notify(id, notification);
        Toast.makeText(context, "Notification arrived", Toast.LENGTH_LONG).show();
    }
}

