package hr.foi.air602.notification.essentials;

import org.joda.time.DateTime;

/**
 * Created by Goran on 10.2.2017..
 */

public interface NotificationListener {
    void onNotificationSchedule(String title, String content, DateTime dt, int notificationID);
    void onNotificationCancel(int notificationID);
}
