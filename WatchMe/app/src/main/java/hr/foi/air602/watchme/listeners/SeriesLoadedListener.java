package hr.foi.air602.watchme.listeners;

import java.util.ArrayList;

import hr.foi.air602.watchme.Series;

/**
 * Created by Goran on 23.11.2016..
 */

public interface SeriesLoadedListener {
    void seriesLoaded(ArrayList<Series> serije, int scroll);
}
