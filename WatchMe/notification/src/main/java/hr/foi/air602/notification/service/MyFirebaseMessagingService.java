package hr.foi.air602.notification.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.util.NotificationUtils;

/**
 * Created by Goran on 18.1.2017..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    private static Activity targetActivity;

    public static void setActivity(Activity activity){
        targetActivity = activity;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "onMessageReceived: From: " + remoteMessage.getFrom());

        if(remoteMessage == null){
            return;
        }

        if(remoteMessage.getNotification() != null){
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if(remoteMessage.getData().size() > 0){
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleNotification(String message){
        if(!NotificationUtils.isAppIsInBackground(getApplicationContext())){
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message",message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        } else {
            //if app is in background , firebase handles it
        }
    }

    private void handleDataMessage(JSONObject json){
        Log.e(TAG, "handleDataMessage: "+ json.toString() );

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "timestamp: " + timestamp);

            if(!NotificationUtils.isAppIsInBackground(getApplicationContext())){
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message",message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            } else {
                Intent resultIntent = new Intent(getApplicationContext(),targetActivity.getClass());
                resultIntent.putExtra("message", message);
                int icon = android.R.mipmap.sym_def_app_icon;
                showNotificationMessage(getApplicationContext(), title, message, timestamp,icon, resultIntent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timestamp, int icon, Intent intent){
        notificationUtils =  new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message,timestamp,icon,intent);
    }


}
