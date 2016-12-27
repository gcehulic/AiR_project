package hr.foi.air602.watchme.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
        Log.d("WATCHME", "onCreate: baza");
        db.execSQL("CREATE TABLE User (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, surname TEXT, mail TEXT, username TEXT, password TEXT);");
        db.execSQL("CREATE TABLE Favorite (id TEXT PRIMARY KEY, slug TEXT, genres TEXT, airs TEXT, network TEXT);");
        db.execSQL("CREATE TABLE UserFavorites (userid INTEGER, favoriteid TEXT, PRIMARY KEY(userid, favoriteid),FOREIGN KEY (userid) REFERENCES User(id), FOREIGN KEY (favoriteid) REFERENCES Favorite(id));");
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Log.d("WATCHME", "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS UserFavorites; CREATE TABLE UserFavorites (userid INTEGER, favoriteid TEXT, PRIMARY KEY(userid, favoriteid),FOREIGN KEY (userid) REFERENCES User(id), FOREIGN KEY (favoriteid) REFERENCES Favorite(id));");
    }
}
