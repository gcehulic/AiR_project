package hr.foi.air602.watchme.database.entities;

import java.io.Serializable;

/**
 * Created by Goran on 20.12.2016..
 */

public class Favorite {
    public String id;
    public String slug;
    public String genres;
    public String airs;
    public String network;
    public String title;

    public Favorite(String id, String title, String slug, String genres, String airs, String network){
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.genres = genres;
        this.airs = airs;
        this.network = network;
    }

}
