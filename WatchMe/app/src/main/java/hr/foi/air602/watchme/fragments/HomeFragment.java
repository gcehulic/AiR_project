package hr.foi.air602.watchme.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import hr.foi.air602.watchme.async_tasks.LoadSeriesAsyncTask;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.listeners.SeriesLoadedListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class HomeFragment extends Fragment implements SeriesLoadedListener,AdapterView.OnItemClickListener {
    private TextView mTextView;
    public static ArrayList<Series> dohvaceneSerije;
    private SeriesListAdapter seriesListAdapter;
    private ListView listaSerija;
    private int brojStranica = 1;
    private ProgressBar mProgressBar;
    private ImageView internetGreska;
    private ListView homeListaSerija;
    private UserFavoriteAdapter userFavoriteAdapter = null;
    private UserAdapter userAdapter = null;
    private List<Favorite> favoriti = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressSpinner);
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF3F51B5, android.graphics.PorterDuff.Mode.MULTIPLY);
        mProgressBar.setVisibility(View.VISIBLE);
        internetGreska = (ImageView) rootView.findViewById(R.id.slikaInternet);
        homeListaSerija = (ListView) rootView.findViewById(R.id.homeListaSerija);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userFavoriteAdapter = new UserFavoriteAdapter(getContext());
        this.userAdapter = new UserAdapter(getContext());
        this.favoriti = this.userFavoriteAdapter.getAllUserFavorites(this.userAdapter.getUserFromSharedPrefs());
        initialize();
    }

    public  void initialize(){
        dohvaceneSerije = new ArrayList<>();
        listaSerija = (ListView) this.getActivity().findViewById(R.id.homeListaSerija);

        if(Utilities.connection(getActivity().getApplicationContext())){
            String url = Utilities.makeUrlSeries("trending",brojStranica);
            this.loadSeries(url);
            setListViewAdapter();
            listaSerija.setOnItemClickListener(this);
            listaSerija.setOnScrollListener(this.onScrollListener());
            internetGreska.setVisibility(View.GONE);

        } else {
            Toast.makeText(this.getContext(),"Niste spojeni na internet",Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(View.GONE);
            internetGreska.setVisibility(View.VISIBLE);
            homeListaSerija.setBackgroundColor(Color.WHITE);
        }

    }

    private void loadSeries(String url){
        new LoadSeriesAsyncTask(this,this.getContext(),url).execute();
    }

    private void setListViewAdapter(){
        this.seriesListAdapter = new SeriesListAdapter(dohvaceneSerije,this.getContext());
        listaSerija.setAdapter(this.seriesListAdapter);
    }

    @Override
    public void seriesLoaded(ArrayList<Series> serije, int scroll) {
        HomeFragment.dohvaceneSerije.addAll(serije);
        for (Series s: HomeFragment.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "seriesLoaded: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        seriesListAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
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

    private AbsListView.OnScrollListener onScrollListener(){
        return new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int prag = 1;
                int broj = listaSerija.getCount();

                if(scrollState == SCROLL_STATE_IDLE){
                    if(listaSerija.getLastVisiblePosition() >= broj - prag){
                        Log.d("HOMEFRAGMENT", "onScrollStateChanged: loading more data");
                        brojStranica++;
                        loadSeries(Utilities.makeUrlSeries("trending",brojStranica));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        };
    }


}
