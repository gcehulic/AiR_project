package hr.foi.air602.notification.notification_options;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationOptions;

/**
 * Created by Goran on 30.1.2017..
 */

public class SilentNotification implements NotificationOptions {

    @Override
    public void showNotification(NotificationCompat.Builder mBuilder, String title, String message, PendingIntent resultPendingIntent, Context context) {
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

        notification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }
}
