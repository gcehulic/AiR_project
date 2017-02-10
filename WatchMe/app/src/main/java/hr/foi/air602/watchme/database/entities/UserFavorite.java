package hr.foi.air602.watchme.database.entities;

/**
 * Created by Goran on 20.12.2016..
 */

public class UserFavorite {
    public int userid;
    public String favoriteid;
    public int notificationId;
    public boolean isNotified;

    public UserFavorite(int userid, String favoriteid, int notificationId, boolean isNotified){
        this.userid = userid;
        this.favoriteid = favoriteid;
        this.notificationId = notificationId;
        this.isNotified = isNotified;
    }
}
