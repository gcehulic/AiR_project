package hr.foi.air602.watchme.strategies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.NotificationListener;
import hr.foi.air602.notification.util.NotificationUtils;
import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.database.entities.UserFavorite;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mateo on 24.1.2017..
 */

public class ScheduledNotificationStrategy implements Strategy {

    private static ScheduledNotificationStrategy INSTANCE = null;
    private static final String TAG = ScheduledNotificationStrategy.class.getSimpleName();
    private List<Favorite> favorites = new ArrayList<>();
    private int minutesToShow = 20;
    private DateTimeZone myTimeZone = DateTimeZone.forID("Europe/Zagreb");
    private FavoriteAdapter favoriteAdapter = null;
    private UserFavoriteAdapter userFavoriteAdapter = null;
    public Context ctx = null;
    private int userId = -1;
    private NotificationListener notificationListener = null;

    public static ScheduledNotificationStrategy getInstance(Context ctx){
        if(INSTANCE == null){
            INSTANCE = new ScheduledNotificationStrategy();
            INSTANCE.favoriteAdapter = new FavoriteAdapter(ctx);
            INSTANCE.userFavoriteAdapter = new UserFavoriteAdapter(ctx);
            INSTANCE.ctx = ctx;
            INSTANCE.notificationListener = NotificationUtils.getInstance(ctx).getListener();
        }

        return INSTANCE;
    }

    @Override
    public void run() {
        Log.e(TAG, "run: strategy run");
        SharedPreferences sp = ctx.getSharedPreferences(Config.SHARED_PREF_OPTIONS, MODE_PRIVATE);
        SharedPreferences sp2 = ctx.getSharedPreferences("loggeduser", MODE_PRIVATE);
        this.userId = sp2.getInt("user",-1);
        this.minutesToShow = sp.getInt("minutes",20);
        this.favorites = userFavoriteAdapter.getAllUnnotifiedFavorites(userId);
        for(Favorite fav : this.favorites){
            Log.e(TAG, "run: " + fav.slug + " --> " + fav.airs);
        }
        this.operateFavorites();
        for(Favorite fav : this.favorites){
            Log.e(TAG, "run: " + fav.slug + " --> " + fav.airs);
            this.notifyShow(fav);
        }
        Log.e(TAG, "run: ended");
    }

    private void operateFavorites(){
        for(Favorite fav : this.favorites){
            fav.airs = this.convertTime(fav.airs);
        }
    }

    private String convertTime(String time){
        String[] timeArray = time.split(";");
        String dayString = timeArray[0];
        String timeString = timeArray[1];
        String[] timeStringArray = timeString.split(":");
        String timezoneString = timeArray[2];

        String[] daysOfWeek = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday", "Sunday"};
        int dayIndex = -1;
        for(int i = 0; i<daysOfWeek.length; i++){
            if(daysOfWeek[i].toLowerCase().equals(dayString.toLowerCase())){
                dayIndex = i;
                break;
            }
        }

        DateTimeZone timeInAPI = DateTimeZone.forID(timezoneString);

        DateTime apiTime = new DateTime(0, 1, 1, Integer.parseInt(timeStringArray[0]), Integer.parseInt(timeStringArray[1]), timeInAPI);
        DateTime myTime = apiTime.withZone(myTimeZone);
        myTime = myTime.minusMinutes(18);

        if(apiTime.getDayOfMonth() < myTime.getDayOfMonth()) dayIndex = (dayIndex+1)%7;

        Log.e(TAG, "convertTime: myTime -  " + myTime.toString());
        Log.e(TAG, "convertTime: apiTime -  " + apiTime.toString());
        Log.e(TAG, "convertTime: day -  " + dayIndex);

        return myTime.hourOfDay().getAsString()+":"+myTime.minuteOfHour().getAsString() + " " + daysOfWeek[dayIndex] + " " + dayIndex;

    }

    public void setMinutes(int minutes){
        this.minutesToShow = minutes;
    }

    private void notifyShow(Favorite favorite){
        Log.e(TAG, "notifyShow: notif needed");
        String[] airsStringArray = favorite.airs.split(" ");
        String title = favorite.title;
        String message = airsStringArray[0] + ", " + airsStringArray[1]+"! Get Ready! :) ";
        String[] vrijemeArray = airsStringArray[0].split(":");
        String sati = vrijemeArray[0];
        String minute = vrijemeArray[1];

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(Integer.parseInt(airsStringArray[2])+1);
        dt = dt.withHourOfDay(Integer.parseInt(sati));
        dt = dt.withMinuteOfHour(Integer.parseInt(minute));
        int notificationId = userFavoriteAdapter.getNotificationId(favorite,userId);
        Log.e(TAG, "notifyShow: id: " + notificationId);
        Log.e(TAG, "notifyShow: before: " + this.minutesToShow);

        this.notificationListener.onNotifyMinutesBeforeShow(this.minutesToShow);
        this.notificationListener.onNotificationSchedule(title,message,dt,notificationId);

        userFavoriteAdapter.setNotified(favorite,userId);
    }



}

