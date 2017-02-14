package hr.foi.air602.watchme.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import hr.foi.air602.watchme.Series;
import hr.foi.air602.watchme.SeriesListAdapter;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.SeriesDetails;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.LoadSeriesRecommendedAsyncTask;
import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.listeners.SeriesLoadedRecommendedListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class RecommendedFragment extends Fragment implements SeriesLoadedRecommendedListener, AdapterView.OnItemClickListener{


    private ArrayList<Series> dohvaceneSerije;
    private ProgressBar mProgressBar;
    private ImageView internetGreska;
    private ListView preporucenoListaSerija=null;
    private TextView nemaPreporuka;
    private SeriesListAdapter seriesListAdapter;
    private FavoriteAdapter favoriteAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preporuceno_layout, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressSpinner);
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF3F51B5, android.graphics.PorterDuff.Mode.MULTIPLY);
        internetGreska = (ImageView) rootView.findViewById(R.id.slikaInternet);
        nemaPreporuka = (TextView) rootView.findViewById(R.id.nemaPreporuka);
        mProgressBar.setVisibility(View.VISIBLE);
        favoriteAdapter = new FavoriteAdapter(this.getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    public void initialize(){
        dohvaceneSerije = new ArrayList<>();
        preporucenoListaSerija = (ListView) this.getActivity().findViewById(R.id.preporucenoListaSerija);
        String genres = favoriteAdapter.getRecommendedGenres();
        if (Utilities.connection(getActivity().getApplicationContext())) {
            if(!genres.equals("")) {
                preporucenoListaSerija.setVisibility(View.VISIBLE);
                String url = Utilities.makeUrlSeriesRecommended("trending", genres);
                this.loadSeries(url);
                setListViewAdapter();
                preporucenoListaSerija.setOnItemClickListener(this);
                internetGreska.setVisibility(View.GONE);
                nemaPreporuka.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);

            } else {
                internetGreska.setVisibility(View.GONE);
                Log.d("WATCHME", "initialize: nema serija ili zanrova u tablici favoriti.");
                nemaPreporuka.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                preporucenoListaSerija.setVisibility(View.GONE);
            }
        } else {
            mProgressBar.setVisibility(View.GONE);
            internetGreska.setVisibility(View.GONE);
            nemaPreporuka.setVisibility(View.GONE);
        }
    }


    private void loadSeries(String url){
        new LoadSeriesRecommendedAsyncTask(this,this.getContext(),url).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Series serije = dohvaceneSerije.get(position);

        Intent i = new Intent(getActivity(), SeriesDetails.class);
        i.putExtra("naslov",serije.getNaslov());
        i.putExtra("godina",serije.getGodina());
        i.putExtra("zanrovi",serije.getGenres());
        i.putExtra("trailer", serije.getTrailer());
        i.putExtra("opis", serije.getOpis());

        startActivity(i);

    }


    @Override
    public void seriesLoadedRecommended(ArrayList<Series> serije) {
        this.dohvaceneSerije.addAll(serije);
        for (Series s: this.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "seriesLoaded: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        seriesListAdapter.notifyDataSetChanged();
    }


    private void setListViewAdapter(){
        this.seriesListAdapter = new SeriesListAdapter(dohvaceneSerije,this.getContext());
        preporucenoListaSerija.setAdapter(this.seriesListAdapter);
    }



}
