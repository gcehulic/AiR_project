package hr.foi.air602.watchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;

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
        contentValues.put("favoritesid",userFavorite.favoriteid);
        //contentValues.put("odabrano",userFavorite.odabrano);


        SQLiteDatabase db = openToWrite();
        return db.insert(TABLE,null,contentValues);
    }
//napraviti update
    public void updateUserFavorite(UserFavorite userFavorite){

    }

    public List<Favorite> getAllUserFavorites(int userid){
        List<Favorite> favorites = new ArrayList<>();

        String[] columns = {"userid","favoriteid", "odabrano"};
        SQLiteDatabase db = openToRead();
        String[] args = {""+userid};
        Cursor cursor = db.query(TABLE,columns,"userid=?",args,null,null,null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            String favoriteid = cursor.getString(cursor.getColumnIndex("favoriteid"));
            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(ctx);
            favorites.add(favoriteAdapter.getFavoriteById(favoriteid));
        }
        return favorites;
    }
}
