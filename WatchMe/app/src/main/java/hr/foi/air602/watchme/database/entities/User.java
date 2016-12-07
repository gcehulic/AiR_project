package hr.foi.air602.watchme.database.entities;

import android.database.Cursor;

/**
 * Created by Mateo on 7.12.2016..
 */

public class User {
    public String name;
    public String surname;
    public String mail;
    public String username;
    public String password;

    public User(String name, String surname, String mail, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

}
