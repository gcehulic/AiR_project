package hr.foi.air602.database.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Mateo on 8.11.2016..
 */

public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "IME  text, PREZIME text, MAIL text, KORISNICKO_IME text, LOZINKA text ); ";

    public  SQLiteDatabase db;
    private final Context context;

    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String ime,String prezime, String mail, String korisnicko_ime, String lozinka)
    {
        ContentValues newValues = new ContentValues();

        newValues.put("IME", ime);
        newValues.put("PREZIME", prezime);
        newValues.put("MAIL", mail);
        newValues.put("KORISNICKO_IME", korisnicko_ime);
        newValues.put("LOZINKA", lozinka);

        db.insert("LOGIN", null, newValues);
    }

    public String getSinlgeEntry(String korisnicko_ime)
    {
        Cursor cursor=db.query("LOGIN", null, " KORISNICKO_IME=?", new String[]{korisnicko_ime}, null, null, null);
        if(cursor.getCount()<1) // korisnicko_ime ne postoji
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String lozinka= cursor.getString(cursor.getColumnIndex("LOZINKA"));
        cursor.close();
        return lozinka;
    }
}
