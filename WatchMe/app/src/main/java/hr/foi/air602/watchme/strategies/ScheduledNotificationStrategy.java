package hr.foi.air602.watchme.strategies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hr.foi.air602.notification.configuration.Config;
import hr.foi.air602.notification.essentials.Strategy;
import hr.foi.air602.notification.service.MyFirebaseMessagingService;
import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;

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
    private boolean work = true;
    public Context ctx = null;

    public static ScheduledNotificationStrategy getInstance(Context ctx){
        if(INSTANCE == null){
            INSTANCE = new ScheduledNotificationStrategy();
            INSTANCE.favoriteAdapter = new FavoriteAdapter(ctx);
            INSTANCE.ctx = ctx;
        }

        return INSTANCE;
    }

    public void updateList(List<Favorite> favorites){
        this.favorites = favorites;
        this.work = false;
    }

    @Override
    public void run() {
        Log.e(TAG, "run: strategy run");
        SharedPreferences sp = ctx.getSharedPreferences(Config.SHARED_PREF_OPTIONS,Context.MODE_PRIVATE);
        this.minutesToShow = sp.getInt("minutes",20);
        this.work = true;
        this.setup();
        for(Favorite fav : this.favorites){
            Log.e(TAG, "run: " + fav.slug + " --> " + fav.airs);
        }
        this.operateFavorites();
        for(Favorite fav : this.favorites){
            Log.e(TAG, "run: " + fav.slug + " --> " + fav.airs);
        }
        do{
            for(Favorite fav : this.favorites){
                this.notifyShow(fav);
            }
            Log.e(TAG, "run: sleeping");
            SystemClock.sleep(55000);
        }while(work);
        Log.e(TAG, "run: ended");
    }

    private void setup(){
        this.favorites = this.favoriteAdapter.getAllFavorites();
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

        String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
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

        if(apiTime.hourOfDay().get() > 12 && myTime.getHourOfDay() < 12) dayIndex = (dayIndex+1)%7;

        return myTime.hourOfDay().getAsString()+":"+myTime.minuteOfHour().getAsString() + " " + daysOfWeek[dayIndex] + " " + dayIndex;

    }

    public void setMinutes(int minutes){
        this.minutesToShow = minutes;
    }

    private void notifyShow(Favorite favorite){
        if(isNotificationNeeded(favorite)){
            Log.e(TAG, "notifyShow: notif needed");
            String[] airsStringArray = favorite.airs.split(" ");
            String title = favorite.title;
            String message = airsStringArray[0] + ", " + airsStringArray[1]+"! Get Ready! :) ";
            MyFirebaseMessagingService.getInstance().sendPush(title, message);

        }
    }

    private boolean isNotificationNeeded(Favorite favorite){
        DateTime now = DateTime.now(myTimeZone);
        String[] airsArray = favorite.airs.split(" ");
        String airsTime = airsArray[0];
        String airsDayName = airsArray[1];
        int airsDayIndex = Integer.parseInt(airsArray[2]);

        String[] airsTimeArray = airsTime.split(":");
        int airsHours = Integer.parseInt(airsTimeArray[0]);
        int airsMinutes = Integer.parseInt(airsTimeArray[1]);

        DateTime timeWhenNotify = now.plusMinutes(this.minutesToShow);
        Log.e(TAG, "isNotificationNeeded: " + this.minutesToShow );
        Log.e(TAG, "isNotificationNeeded: " + favorite.slug + " " + favorite.airs );
        Log.e(TAG, "isNotificationNeeded: " + timeWhenNotify.toString());
        Log.e(TAG, "isNotificationNeeded: day "+timeWhenNotify.dayOfWeek().getAsText(Locale.ENGLISH).toLowerCase()+" <> " + airsDayName.toLowerCase());
        Log.e(TAG, "isNotificationNeeded:  hour " + timeWhenNotify.getHourOfDay()+" <> "+ airsHours );
        Log.e(TAG, "isNotificationNeeded: minutes " + timeWhenNotify.getMinuteOfHour() + " <> " + airsMinutes );

        if(timeWhenNotify.dayOfWeek().getAsText(Locale.ENGLISH).toLowerCase().equals(airsDayName.toLowerCase()) &&
                timeWhenNotify.hourOfDay().get() == airsHours &&
                timeWhenNotify.minuteOfHour().get() == airsMinutes){
            return true;
        }
        return false;
    }


}

