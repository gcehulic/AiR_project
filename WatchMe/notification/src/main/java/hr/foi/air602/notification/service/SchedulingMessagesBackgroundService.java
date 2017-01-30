package hr.foi.air602.notification.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import hr.foi.air602.notification.essentials.Strategy;

/**
 * Created by markopc on 24.1.2017..
 */

public class SchedulingMessagesBackgroundService extends IntentService {

    private static final String TAG = SchedulingMessagesBackgroundService.class.getSimpleName();
    private static Strategy serviceStrategy = null;

    public SchedulingMessagesBackgroundService() {
        super("schedulingNotifications");
    }

    public static void setStrategy(Strategy strategy){
        serviceStrategy = strategy;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent: working began" );
        if(this.serviceStrategy != null){
            this.serviceStrategy.run();
        } else Log.e(TAG, "strategy not specified" );
        Log.e(TAG, "onHandleIntent: working ended" );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: The Service has started");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: The Service has stopped");
        super.onDestroy();
    }
}