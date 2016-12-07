package hr.foi.air602.watchme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mateo on 7.12.2016..
 */

public class DataAdapter {

    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    private DbHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public DataAdapter(Context context) {
        this.context = context;
    }

    // otvaranje veze prema bazi podataka za čitanje
    public SQLiteDatabase openToRead() throws android.database.SQLException {
        sqLiteHelper = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return sqLiteDatabase;
    }

    // otvaranje veze prema bazi podataka za pisanje
    public SQLiteDatabase openToWrite() throws android.database.SQLException {
        sqLiteHelper = new DbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return sqLiteDatabase;
    }

    // zatvaranje veze prema bazi podataka
    public void close() {
        sqLiteDatabase.close();
        sqLiteHelper.close();
    }

}
