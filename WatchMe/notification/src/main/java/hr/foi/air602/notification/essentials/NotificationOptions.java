package hr.foi.air602.notification.essentials;

/**
 * Created by Goran on 26.1.2017..
 */

/*
    Sučelje služi za definiranje ima li notifikacija zvuka i/ili vibraciju ili ništa od toga.
 */
public interface NotificationOptions {
    boolean getSound();
    boolean getVibration();
}
