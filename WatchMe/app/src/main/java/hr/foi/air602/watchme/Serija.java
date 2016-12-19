package hr.foi.air602.watchme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Goran on 23.11.2016..
 */

public class Serija {

    private String naslov;
    private int godina;

    private int id_trakt, id_tvdb, id_imdb, id_tvrage;
    private String slug, imdb, genres, trailer, opis, overview;

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

            this.trailer = jsonObject.getString("trailer");

            this.id_trakt = jsonObject.getJSONObject("ids").getInt("trakt");
            this.id_imdb = jsonObject.getJSONObject("ids").getInt("imdb");
            this.id_tvdb = jsonObject.getJSONObject("ids").getInt("tvdb");
            this.id_tvrage = jsonObject.getJSONObject("ids").getInt("tvrage");
            this.slug = jsonObject.getJSONObject("ids").getString("slug");
            this.imdb = jsonObject.getJSONObject("ids").getString("imdb");

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

    public int getId_trakt() {
        return id_trakt;
    }

    public void setId_trakt(int id_trakt) {
        this.id_trakt = id_trakt;
    }

    public int getId_tvdb() {
        return id_tvdb;
    }

    public void setId_tvdb(int id_tvdb) {
        this.id_tvdb = id_tvdb;
    }

    public int getId_imdb() {
        return id_imdb;
    }

    public void setId_imdb(int id_imdb) {
        this.id_imdb = id_imdb;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


    public int getId_tvrage() {
        return id_tvrage;
    }

    public void setId_tvrage(int id_tvrage) {
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
}
