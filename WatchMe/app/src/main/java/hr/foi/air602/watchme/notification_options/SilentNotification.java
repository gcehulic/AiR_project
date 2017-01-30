package hr.foi.air602.watchme.notification_options;

import hr.foi.air602.notification.essentials.NotificationOptions;

/**
 * Created by Goran on 30.1.2017..
 */

public class SilentNotification implements NotificationOptions {
    @Override
    public boolean getSound() {
        return false;
    }

    @Override
    public boolean getVibration() {
        return false;
    }
}
