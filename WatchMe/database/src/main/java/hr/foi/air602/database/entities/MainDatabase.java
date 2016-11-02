package hr.foi.air602.database.entities;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Goran on 2.11.2016..
 */
@Database(name = MainDatabase.NAME,version = MainDatabase.VERSION)
public class MainDatabase {
    public static final String NAME="main";
    public static final int VERSION=1;
}
