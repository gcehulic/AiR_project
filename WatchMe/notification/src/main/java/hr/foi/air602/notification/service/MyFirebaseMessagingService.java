package hr.foi.air602.notification.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.configuration.EndPoints;
import hr.foi.air602.notification.essentials.MyVolley;
import hr.foi.air602.notification.essentials.Strategy;
import hr.foi.air602.notification.util.NotificationUtils;

/**
 * Created by Goran on 18.1.2017..
 */

/**
 * Klasa služi za prikaz notifikacija i obrada primljenih podataka
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private Context ctx;
    private String email = null;

    private static MyFirebaseMessagingService INSTANCE = new MyFirebaseMessagingService();

    public MyFirebaseMessagingService(){}

    public static MyFirebaseMessagingService getInstance(){
        return INSTANCE;
    }

    public void setContext(Context context){
        this.ctx = context;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);

            Intent resultIntent = new Intent(getApplicationContext(),SchedulingMessagesBackgroundService.class);
            resultIntent.putExtra("message", message);
            showNotificationMessage(getApplicationContext(), title, message, resultIntent);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Prikazivanje notifikacije samo sa tekstom
     */
    public void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    public void setup(Context context, String email){
        this.email = email;
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(ctx.getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

        displayFirebaseRegId();
        MyFirebaseInstanceIDService.getInstance(context).setEmail(email);
        MyFirebaseInstanceIDService.getInstance(context).registerDeviceToService();
    }

    public void displayFirebaseRegId() {
        SharedPreferences pref = ctx.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
    }

    //Registrira Broadcast Receiver-e tako da activity bude obaviješten svaki puta kada dođe poruka.
    //Jedan sluša dok dođe notifikacija za uspješnu registraciju, a drugi sluša push notifikacije.
    //Na kraju se obrišu svi notifi kada se otvori activity aplikacije.
    public void registerBroadcast(){
        LocalBroadcastManager.getInstance(ctx).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(ctx).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(ctx);
    }

    public void unregisterBroadcast(){
        LocalBroadcastManager.getInstance(ctx).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    //Metoda šalje zahtjev web servisu da web servis vrati notifikaciju natrag vlasniku email-a
    public void sendPush(final String title, final String message) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);
                params.put("email", email);
                return params;
            }
        };

        MyVolley.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    //Pokreće IntentService koji je u pozadini.
    public void schedulingNotifs(){
        Intent serviceIntent = new Intent(ctx, SchedulingMessagesBackgroundService.class);
        ctx.startService(serviceIntent);
    }
}
