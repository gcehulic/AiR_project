package hr.foi.air602.watchme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Goran on 23.11.2016..
 */

public class Series {

    private String naslov;
    private int godina;


    private String idTrakt, idTvdb, idTvrage;
    private String slug, imdb, genres, trailer, opis;
    private String zanrovi;
    private String emitiranje, mreza;

    public Series(){
        naslov = "";
        godina = 0;
    }
    public Series(JSONObject jsonObject) {

        try {
            this.naslov = jsonObject.getString("title");
            this.godina = jsonObject.getInt("year");
            this.opis = jsonObject.getString("overview");

            JSONArray zanr = jsonObject.getJSONArray("genres");
            this.genres = zanr.toString();
            this.zanrovi = "";
            for(int i = 0; i < zanr.length(); i++){
                this.zanrovi += zanr.get(i).toString();
                if(i < zanr.length()-1) this.zanrovi += ",";
            }

            this.trailer = jsonObject.getString("trailer");
            this.emitiranje = jsonObject.getJSONObject("airs").getString("day")+";"+jsonObject.getJSONObject("airs").getString("time")+";"+jsonObject.getJSONObject("airs").getString("timezone");
            this.mreza = jsonObject.getString("network");


            this.idTrakt = jsonObject.getJSONObject("ids").getString("trakt");
            this.idTvdb = jsonObject.getJSONObject("ids").getString("tvdb");
            this.idTvrage = jsonObject.getJSONObject("ids").getString("tvrage");
            this.slug = jsonObject.getJSONObject("ids").getString("slug");
            this.imdb = jsonObject.getJSONObject("ids").getString("imdb");
            Log.d("WATCHME", "Series: slug:"+this.slug);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public String getIdTrakt() {
        return idTrakt;
    }

    public void setIdTrakt(String idTrakt) {
        this.idTrakt = idTrakt;
    }

    public String getIdTvdb() {
        return idTvdb;
    }

    public void setIdTvdb(String idTvdb) {
        this.idTvdb = idTvdb;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


    public String getIdTvrage() {
        return idTvrage;
    }

    public void setIdTvrage(String idTvrage) {
        this.idTvrage = idTvrage;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getZanrovi() {
        return zanrovi;
    }

    public String getEmitiranje() {
        return emitiranje;
    }

    public String getMreza() {
        return mreza;
    }
}
