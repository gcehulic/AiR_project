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

    public static final String YOUTUBE_API_KEY = "AIzaSyA0Fqtk9oTJzXTL9nyozp0RLtlvTSCUg6A";

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

    public static String izradaUrlSerijePoId(String id){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("search").append("/").append("trakt").append("/").append(id).append("?type=show").append("&extended=full");
        Log.d("UTIL", "izradaUrlSerijeID: "+url.toString());
        return url.toString();
    }

    public static String izradaUrlSerijePreporuceno(String kategorija, String zanrovi){
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL).append("shows").append("/").append(kategorija).append("?limit=").append(20).append("&extended=full").append("&genres=").append(zanrovi);
        Log.d("UTIL", "izradaUrlSerije: "+url.toString());
        return url.toString();
    }

}
