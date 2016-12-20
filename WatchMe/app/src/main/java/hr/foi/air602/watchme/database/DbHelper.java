package hr.foi.air602.watchme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mateo on 7.12.2016..
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, mail TEXT, username TEXT, password TEXT);");
        db.execSQL("CREATE TABLE Favorite (id TEXT PRIMARY KEY, slug TEXT, genres TEXT, airs TEXT, network TEXT);");
        db.execSQL("CREATE TABLE UserFavorites (userid INTEGER FOREIGN KEY (userid) REFERENCES User(id), favoriteid TEXT FOREIGN KEY (favoriteid) REFERENCES Favorite(id), PRIMARY KEY(userid, favoriteid)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
