package hr.foi.air602.watchme.database.entities;

/**
 * Created by Goran on 20.12.2016..
 */

public class Favorite {
    public String id;
    public String slug;
    public String genres; // odvojeno ;
    public String airs; //json
    public String network;

    public Favorite(String id, String slug, String genres, String airs, String network){
        this.id = id;
        this.slug = slug;
        this.genres = genres;
        this.airs = airs;
        this.network = network;
    }

}