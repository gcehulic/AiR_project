package hr.foi.air602.watchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.database.entities.User;

/**
 * Created by Mateo on 7.12.2016..
 */

public class UserAdapter extends DataAdapter{

    private static final String TABLE = "User";
    public static final String KEY_ID = "id";
    private Context context = null;

    public UserAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public long insertUser(User user){
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", user.name);
        contentValues.put("surname", user.surname);
        contentValues.put("mail", user.mail);
        contentValues.put("username", user.username);
        contentValues.put("password", user.password);

        SQLiteDatabase db = openToWrite();

        return db.insert(TABLE, null, contentValues);
    }

    public List<User> getAllUsers(){
        List<User> result = new ArrayList<User>();

        String[] columns = new String[]{KEY_ID, "name", "surname", "mail", "username", "password"};
        SQLiteDatabase db = openToRead();

        Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);

        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            String mail = cursor.getString(cursor.getColumnIndex("mail"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            User user = new User(name, surname, mail, username, password);
            result.add(user);
        }

        cursor.close();

        return result;

    }

    public int getUserId(String username, String password){
        String[] columns = new String[]{KEY_ID, "name", "surname", "mail", "username", "password"};
        SQLiteDatabase db = openToRead();

        String[] args = {username, password};
        Cursor cursor = db.query(TABLE, columns, "username=? AND password=?", args, null, null, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        cursor.close();
        return id;
    }

    public String getUserEmail(String username, String password){
        String[] columns = new String[]{KEY_ID, "name", "surname", "mail", "username", "password"};
        SQLiteDatabase db = openToRead();

        String[] args = {username, password};
        Cursor cursor = db.query(TABLE, columns, "username=? AND password=?", args, null, null, null);
        cursor.moveToFirst();
        String email = cursor.getString(cursor.getColumnIndex("mail"));
        cursor.close();
        return email;
    }

    public int getUserFromSharedPrefs(){
        SharedPreferences sp = context.getSharedPreferences("loggeduser",Context.MODE_PRIVATE);
        return sp.getInt("user",0);
    }

    public void createDefaultUser(){
        if(!this.doesDefaultUserExists()){
            Log.e("UserAdapter", "createDefaultUser: Default user doesn't exists.");
            long result = this.insertDefaultUser();
        } else Log.e("UserAdapter", "createDefaultUser: Default user exists.");
    }

    private boolean doesDefaultUserExists(){
        String[] columns = new String[]{KEY_ID, "name", "surname", "mail", "username", "password"};
        SQLiteDatabase db = openToRead();

        String[] args = {"user", "password", "user@user.us"};
        Cursor cursor = db.query(TABLE, columns, "username=? AND password=? AND mail = ?", args, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count == 1;
    }

    private long insertDefaultUser(){
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", "user");
        contentValues.put("surname", "user");
        contentValues.put("mail", "user@user.us");
        contentValues.put("username", "user");
        contentValues.put("password", "password");

        SQLiteDatabase db = openToWrite();

        return db.insert(TABLE, null, contentValues);
    }
}
