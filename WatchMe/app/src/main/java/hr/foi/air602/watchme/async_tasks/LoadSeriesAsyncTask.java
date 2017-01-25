package hr.foi.air602.watchme.async_tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import hr.foi.air602.watchme.Series;
import hr.foi.air602.watchme.listeners.SeriesLoadedListener;

/**
 * Created by Goran on 23.11.2016..
 */

public class LoadSeriesAsyncTask extends AsyncTask<String, String, String> {

    private SeriesLoadedListener seriesLoadedListener;
    Context context;
    String url;
    int scroll;

    public LoadSeriesAsyncTask(SeriesLoadedListener seriesLoadedListener, Context context, String url) {
        this.seriesLoadedListener = seriesLoadedListener;
        this.context = context;
        this.url = url;
        this.scroll = 0;
    }

    public LoadSeriesAsyncTask(SeriesLoadedListener seriesLoadedListener, Context context, String url, int scroll) {
        this.seriesLoadedListener = seriesLoadedListener;
        this.context = context;
        this.url = url;
        this.scroll = scroll;
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            if(this.isCancelled())return "otkazano";
            URL obj = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("trakt-api-version", "2");
            con.setRequestProperty("trakt-api-key", "d07ed0684f98caa1bfc0d0894e157a2f558b8bb47cadb449e11a86556b40c8d0");

            int responseCode = con.getResponseCode();
            if(this.isCancelled())return "otkazano";

            if(responseCode == 200){
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = in.readLine()) != null){
                    response.append(line);
                }
                in.close();
                if(this.isCancelled())return "otkazano";
                return response.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.equals("otkazano") || this.isCancelled()){
            return;
        }
        ArrayList<Series> seriesList = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject serija = jsonArray.getJSONObject(i).getJSONObject("show");
                seriesList.add(new Series(serija));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(s.equals("otkazano") || this.isCancelled()){
            return;
        }
        seriesLoadedListener.seriesLoaded(seriesList, this.scroll);
    }

}
