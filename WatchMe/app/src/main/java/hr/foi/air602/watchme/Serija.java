package hr.foi.air602.watchme;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Goran on 23.11.2016..
 */

public class Serija {

    private String naslov;
    private int godina;


    private String id_trakt, id_tvdb, id_imdb, id_tvrage;
    private String slug, imdb, genres, trailer, opis, overview;
    private String zanrovi;
    private String emitiranje, mreza;

    public Serija(){
        naslov = "";
        godina = 0;
    }
    public Serija(JSONObject jsonObject) {

        try {
            this.naslov = jsonObject.getString("title");
            this.godina = jsonObject.getInt("year");
            this.opis = jsonObject.getString("overview");

            JSONArray zanr = jsonObject.getJSONArray("genres");
            this.genres = zanr.toString();
            this.zanrovi = "";
            for(int i = 0; i < zanr.length(); i++){
                this.zanrovi += zanr.get(i).toString();
                if(i < zanr.length()-1) this.zanrovi += ";";
            }

            this.trailer = jsonObject.getString("trailer");
            this.emitiranje = jsonObject.getJSONObject("airs").getString("day")+";"+jsonObject.getJSONObject("airs").getString("time")+";"+jsonObject.getJSONObject("airs").getString("timezone");
            this.mreza = jsonObject.getString("network");


            this.id_trakt = jsonObject.getJSONObject("ids").getString("trakt");
            this.id_tvdb = jsonObject.getJSONObject("ids").getString("tvdb");
            this.id_tvrage = jsonObject.getJSONObject("ids").getString("tvrage");
            this.slug = jsonObject.getJSONObject("ids").getString("slug");
            this.imdb = jsonObject.getJSONObject("ids").getString("imdb");
            Log.d("WATCHME", "Serija: slug:"+this.slug);
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

    public String getId_trakt() {
        return id_trakt;
    }

    public void setId_trakt(String id_trakt) {
        this.id_trakt = id_trakt;
    }

    public String getId_tvdb() {
        return id_tvdb;
    }

    public void setId_tvdb(String id_tvdb) {
        this.id_tvdb = id_tvdb;
    }

/*    public String getId_imdb() {
        return id_imdb;
    }

    public void setId_imdb(int id_imdb) {
        this.id_imdb = id_imdb;
    }
*/
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


    public String getId_tvrage() {
        return id_tvrage;
    }

    public void setId_tvrage(String id_tvrage) {
        this.id_tvrage = id_tvrage;
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
