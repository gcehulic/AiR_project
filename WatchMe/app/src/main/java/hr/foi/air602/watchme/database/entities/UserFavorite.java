package hr.foi.air602.watchme.database.entities;

/**
 * Created by Goran on 20.12.2016..
 */

public class UserFavorite {
    public int userid;
    public String favoriteid;

    public UserFavorite(int userid, String favoriteid){
        this.userid = userid;
        this.favoriteid = favoriteid;
    }
}
