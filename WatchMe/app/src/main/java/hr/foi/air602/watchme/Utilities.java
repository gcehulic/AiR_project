package hr.foi.air602.watchme;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by Goran on 23.11.2016..
 */

public class Utilities {

    public static String BASE_URL = "https://api.trakt.tv/";

    public static final String YOUTUBE_API_KEY = "AIzaSyA0Fqtk9oTJzXTL9nyozp0RLtlvTSCUg6A";

    public static boolean connection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) return true;
        else return false;
    }

    public static String makeUrlSeries(String kategorija, int stranica){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("shows").append("/").append(kategorija).append("?page=").append(stranica).append("&extended=full");
        Log.d("UTIL", "makeUrlSeries: "+url.toString());
        return url.toString();
    }

    public static String makeUrlSeriesFromId(String id){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("search").append("/").append("trakt").append("/").append(id).append("?type=show").append("&extended=full");
        Log.d("UTIL", "izradaUrlSerijeID: "+url.toString());
        return url.toString();
    }

    public static String makeUrlSeriesRecommended(String kategorija, String zanrovi){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("shows").append("/").append(kategorija).append("?limit=").append(20).append("&extended=full").append("&genres=").append(zanrovi);
        Log.d("UTIL", "makeUrlSeries: "+url.toString());
        return url.toString();
    }

    public static String convertTime(String time){
        DateTimeZone myTimeZone = DateTimeZone.forID("Europe/Zagreb");
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

        String myMinutes = null;
        if(myTime.minuteOfHour().get() < 10) myMinutes = "0" + myTime.minuteOfHour().getAsString();
        else myMinutes = myTime.minuteOfHour().getAsString();
        return myTime.hourOfDay().getAsString()+":"+myMinutes + " " + daysOfWeek[dayIndex] + " " + dayIndex;

    }
}
