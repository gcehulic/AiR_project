package hr.foi.air602.watchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.database.entities.UserFavorite;

/**
 * Created by Goran on 20.12.2016..
 */

public class UserFavoriteAdapter extends DataAdapter {
    private static final String TABLE = "UserFavorites";
    private Context ctx = null;

    public UserFavoriteAdapter(Context context) {
        super(context);
        this.ctx = context;
    }

    public long insertUserFavorite(UserFavorite userFavorite){
        ContentValues contentValues = new ContentValues();

        contentValues.put("userid",userFavorite.userid);
        contentValues.put("favoriteid",userFavorite.favoriteid);
        contentValues.put("notificationId", this.generateNewId(userFavorite.userid));

        SQLiteDatabase db = openToWrite();
        return db.insert(TABLE,null,contentValues);
    }

    public long deleteUserFavorite(UserFavorite userFavorite){
        SQLiteDatabase db = openToWrite();
        String[] args = {userFavorite.userid+"",userFavorite.favoriteid};
        return db.delete(TABLE,"userid = ? and favoriteid = ?",args);
    }

    public boolean doesFavoriteExists(int userID, String favoriteID){
        List<Favorite> favorites = this.getAllUserFavorites(userID);
        for (Favorite f : favorites ) {
            if(f.id.equals(favoriteID)) return true;
        }
        return false;
    }

    public int generateNewId(int userID){
        String[] columns = {"userid","favoriteid","notificationId"};
        SQLiteDatabase db = openToRead();
        String[] args = {""+userID};
        Cursor cursor = db.query(TABLE,columns,"userid=?",args,null, null,"notificationId desc","1");
        int broj = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Log.e("USERFAV", "generateNewId: ima redova vracenih");
            broj = cursor.getInt(cursor.getColumnIndex("notificationId"));
        }
        Log.e("USERFAV", "generateNewId: broj = " + broj );
        return ++broj;
    }


    public List<Favorite> getAllUserFavorites(int userid){
        List<Favorite> favorites = new ArrayList<>();

        String[] columns = {"userid","favoriteid", "notificationId", "isNotified"};
        SQLiteDatabase db = openToRead();
        String[] args = {""+userid};
        Cursor cursor = db.query(TABLE,columns,"userid=?",args,null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            String favoriteid = cursor.getString(cursor.getColumnIndex("favoriteid"));
            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(ctx);
            favorites.add(favoriteAdapter.getFavoriteById(favoriteid));
        }
        for(Favorite f : favorites){
            Log.e("USERFAVORITE", "getAllUserFavorites: " + f.title + " " + f.id + " "  + f.airs);
        }
        cursor.close();
        return favorites;
    }

    public List<Favorite> getAllUnnotifiedFavorites(int userid){
        List<Favorite> favorites = new ArrayList<>();

        String[] columns = {"userid","favoriteid", "notificationId", "isNotified"};
        SQLiteDatabase db = openToRead();
        String[] args = {""+userid, "false"};
        Cursor cursor = db.query(TABLE,columns,"userid = ? and isNotified  = ?",args,null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            String favoriteid = cursor.getString(cursor.getColumnIndex("favoriteid"));
            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(ctx);
            favorites.add(favoriteAdapter.getFavoriteById(favoriteid));
        }
        for(Favorite f : favorites){
            Log.e("USERFAVORITE", "getAllUserFavorites: " + f.title + " " + f.id + " "  + f.airs);
        }
        cursor.close();
        return favorites;
    }

    public int getNotificationId(Favorite favorite, int userId ){
        String[] columns = {"userid","favoriteid", "notificationId", "isNotified"};
        SQLiteDatabase db = openToRead();
        String[] args = {favorite.id, ""+userId};
        Cursor cursor = db.query(TABLE,columns,"favoriteid = ? and userid = ?",args,null,null,null);
        int notifId = -1;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            notifId = cursor.getInt(cursor.getColumnIndex("notificationId"));
        }
        cursor.close();
        return notifId;
    }

    public void setNotified(Favorite favorite, int userId){
        String[] columns = {"userid","favoriteid", "notificationId", "isNotified"};
        SQLiteDatabase db = openToRead();
        ContentValues content = new ContentValues();
        content.put("isNotified",true);
        String[] args = {favorite.id, ""+userId};
        int  result = db.update(TABLE,content,"favoriteid = ? and userid = ?",args);
    }
}
