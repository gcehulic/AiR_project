package hr.foi.air602.watchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.database.entities.User;

/**
 * Created by Goran on 20.12.2016..
 */

public class FavoriteAdapter extends DataAdapter {

    private static final String TABLE = "Favorite";

    public FavoriteAdapter(Context context) {
        super(context);
    }


    public long insertFavorite(Favorite favorite){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",favorite.id);
        contentValues.put("slug",favorite.slug);
        contentValues.put("genres",favorite.genres);
        contentValues.put("airs",favorite.airs);
        contentValues.put("network",favorite.network);

        SQLiteDatabase db = openToWrite();
        return db.insert(TABLE,null,contentValues);
    }

    // pregled svih favorita return null;
    public List<Favorite> getAllFavorites(){
        List<Favorite> result = new ArrayList<>();

        String[] columns = new String[]{"id", "slug", "genres", "airs", "network"};
        SQLiteDatabase db = openToRead();

        Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);

        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String slug = cursor.getString(cursor.getColumnIndex("slug"));
            String genres = cursor.getString(cursor.getColumnIndex("genres"));
            String airs = cursor.getString(cursor.getColumnIndex("airs"));
            String network = cursor.getString(cursor.getColumnIndex("network"));
            Favorite favorite = new Favorite(id,slug,genres,airs,network);
            result.add(favorite);
        }

        return result;

    }

    public Favorite getFavoriteById(String favoriteid){
        String[] columns = new String[]{"id", "slug", "genres", "airs", "network"};
        SQLiteDatabase db = openToRead();

        String[] args = {favoriteid};
        Cursor cursor = db.query(TABLE, columns, "id=?",args, null, null, null);
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex("id"));
        String slug = cursor.getString(cursor.getColumnIndex("slug"));
        String genres = cursor.getString(cursor.getColumnIndex("genres"));
        String airs = cursor.getString(cursor.getColumnIndex("airs"));
        String network = cursor.getString(cursor.getColumnIndex("network"));
        Favorite favorite = new Favorite(id,slug,genres,airs,network);
        return favorite;
    }

    public long deleteFavorite(Favorite favorite){
        SQLiteDatabase db = openToWrite();
        String[] args = {favorite.id};
        return db.delete(TABLE,"id = ?",args);
    }

    public String getRecommendedGenres(){
        String result = "";

        String[] columns = new String[]{"id", "genres"};
        SQLiteDatabase db = openToRead();

        Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);

        String tempGenres = "";
        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            String genres = cursor.getString(cursor.getColumnIndex("genres"));
            tempGenres += genres;
            if(!cursor.isLast()){
                tempGenres += ",";
            }

        }
        if(tempGenres == "") return "";

        String[] tempGenresArray = tempGenres.split(",");
        JSONObject tempGenresJson = new JSONObject();
        int count = 0;
        try{
            for(String genre : tempGenresArray) {
                if(genre == "") continue;
                if (tempGenresJson.has(genre)) {
                    count = tempGenresJson.getInt(genre);
                    count++;
                    tempGenresJson.put(genre, count);
                } else {
                    tempGenresJson.put(genre, 0);
                }
            }

            int maxCount = 0;
            for(Iterator<String> iter = tempGenresJson.keys(); iter.hasNext();){
                String genre = iter.next();
                if(maxCount <= tempGenresJson.getInt(genre)){
                    result = genre;
                    maxCount = tempGenresJson.getInt(genre);
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }



        return result;

    }
}
