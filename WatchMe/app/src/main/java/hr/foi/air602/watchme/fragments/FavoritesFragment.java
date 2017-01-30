package hr.foi.air602.watchme.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.Series;
import hr.foi.air602.watchme.SeriesListAdapter;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.SeriesDetails;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.LoadSeriesFromIdAsyncTask;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.listeners.SeriesLoadedFromIdListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class FavoritesFragment extends Fragment implements AdapterView.OnItemClickListener, SeriesLoadedFromIdListener {

    private UserFavoriteAdapter userFavoriteAdapter = null;
    private UserAdapter userAdapter = null;
    private List<Favorite> favoriti = new ArrayList<>();
    private ListView listaSerija = null;
    private ProgressBar mProgressBar;
    public static ArrayList<Series> dohvaceneSerije;
    public static SeriesListAdapter seriesListAdapter;
    private TextView nemaFavorita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pregled_layout, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressSpinner);
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF3F51B5, android.graphics.PorterDuff.Mode.MULTIPLY);
        nemaFavorita = (TextView) rootView.findViewById(R.id.nemaFavorita);
        mProgressBar.setVisibility(View.VISIBLE);

        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init();
    }
   private  void initialize(){

        dohvaceneSerije = new ArrayList<>();

        listaSerija = (ListView) this.getActivity().findViewById(R.id.pregledListaSerija);

        if(Utilities.connection(getActivity().getApplicationContext())){

            this.favoriti = this.userFavoriteAdapter.getAllUserFavorites(this.userAdapter.getUserFromSharedPrefs());

            if(this.favoriti.size() > 0) {
                nemaFavorita.setVisibility(View.GONE);
                for (Favorite f : this.favoriti) {
                    Log.d("WATCHME", "onViewCreated: id:" + f.id + " slug:" + f.slug);
                    String url = Utilities.makeUrlSeriesFromId(f.id);
                    this.loadSeriesFromId(url);
                }
            } else {
                Log.d("WATCHME", "onViewCreated: favoriti prazni");
                mProgressBar.setVisibility(View.GONE);
                nemaFavorita.setVisibility(View.VISIBLE);
            }

            setListViewAdapter();
            listaSerija.setOnItemClickListener(this);
            listaSerija.setOnScrollListener(this.onScrollListener());

        } else {
            Toast.makeText(this.getContext(),"Niste spojeni na internet",Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(View.GONE);
          //  homeListaSerija.setBackgroundColor(Color.WHITE);
        }

    }

    private AbsListView.OnScrollListener onScrollListener(){
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        };
    }

    private void loadSeriesFromId(String url){
        new LoadSeriesFromIdAsyncTask(this,this.getContext(),url).execute();
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
    public void seriesLoadedFromId(Series series) {
        FavoritesFragment.dohvaceneSerije.add(series);
        for (Series s: HomeFragment.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "seriesLoaded: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        seriesListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    private void setListViewAdapter(){
        this.seriesListAdapter = new SeriesListAdapter(dohvaceneSerije,this.getContext());
        listaSerija.setAdapter(this.seriesListAdapter);

    }

    public void refresh() {
        if(Utilities.connection(getActivity().getApplicationContext())) {
            this.userFavoriteAdapter = new UserFavoriteAdapter(getContext());
            this.userAdapter = new UserAdapter(getContext());
            this.favoriti = this.userFavoriteAdapter.getAllUserFavorites(this.userAdapter.getUserFromSharedPrefs());

            if (this.favoriti.size() > 0) {
                for (Favorite f : this.favoriti) {
                    Log.d("WATCHME", "onViewCreated: id:" + f.id + " slug:" + f.slug +" zanrovi:" + f.genres + " airs:" + f.airs + " network:"+f.network);

                }
            } else {
                Log.d("WATCHME", "onViewCreated: favoriti prazni");
            }
            initialize();
            seriesListAdapter.notifyDataSetChanged();
        }
        else{
            mProgressBar.setVisibility(View.GONE);
        }
    }


}

