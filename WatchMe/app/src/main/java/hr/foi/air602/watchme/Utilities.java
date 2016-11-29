package hr.foi.air602.watchme;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Goran on 23.11.2016..
 */

public class Utilities {

    public static String BASE_URL = "https://api.trakt.tv/";

    public static boolean povezanost(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) return true;
        else return false;
    }

    public static String izradaUrlSerije(String kategorija, int stranica){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("shows").append("/").append(kategorija).append("?page=").append(stranica).append("&extended=full");
        Log.d("UTIL", "izradaUrlSerije: "+url.toString());
        return url.toString();
    }
}
