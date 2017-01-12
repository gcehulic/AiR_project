package hr.foi.air602.watchme.fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.PopisSerijaAdapter;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.Serija;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.DohvatSerijaAsyncTask;
import hr.foi.air602.watchme.async_tasks.DohvatSerijaPreporucenoAsyncTask;
import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.listeners.SerijeDohvacenePreporucenoListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class PreporucenoFragment extends Fragment implements SerijeDohvacenePreporucenoListener {


    private ArrayList<Serija> dohvaceneSerije;
    private ProgressBar mProgressBar;
    private ImageView internetGreska;
  //  private ListView preporucenoListaSerija;
    private ListView preporucenoListaSerija=null;

    private PopisSerijaAdapter popisSerijaAdapter;
    private FavoriteAdapter favoriteAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preporuceno_layout, container, false);
      //  preporucenoListaSerija = (ListView) rootView.findViewById(R.id.preporuceno_lista_serija);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_spinner);
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF3F51B5, android.graphics.PorterDuff.Mode.MULTIPLY);
        internetGreska = (ImageView) rootView.findViewById(R.id.slikaInternet);
        favoriteAdapter = new FavoriteAdapter(this.getContext());

        initialize();
        return rootView;
    }


    public void initialize(){

        dohvaceneSerije = new ArrayList<>();

        preporucenoListaSerija = (ListView) this.getActivity().findViewById(R.id.preporuceno_lista_serija);
        String genres = favoriteAdapter.getRecommendedGenres();
        if(!genres.equals("")) {
            if (Utilities.povezanost(getActivity().getApplicationContext())) {
                String url = Utilities.izradaUrlSerijePreporuceno("trending", genres);
                this.dohvatSerija(url);
                setListViewAdapter();
                //listaSerija.setOnItemClickListener(this);
                //listaSerija.setOnScrollListener(this.onScrollListener());
                internetGreska.setVisibility(View.GONE);

            } else {
                Toast.makeText(this.getContext(), "Niste spojeni na internet", Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
                internetGreska.setVisibility(View.VISIBLE);
                preporucenoListaSerija.setBackgroundColor(Color.WHITE);
            }

        } else {
            internetGreska.setVisibility(View.GONE);
            Log.d("WATCHME", "initialize: nema serija ili zanrova u tablici favoriti.");
            //TODO prikazati da nema preporuka i maknuti progress bar
        }

    }


    private void dohvatSerija(String url){
        new DohvatSerijaPreporucenoAsyncTask(this,this.getContext(),url).execute();
    }


    @Override
    public void serijeDohvacenePreporuceno(ArrayList<Serija> serije) {
        this.dohvaceneSerije.addAll(serije);
        for (Serija s: this.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "serijeDohvacene: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        popisSerijaAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    private void setListViewAdapter(){
        this.popisSerijaAdapter = new PopisSerijaAdapter(dohvaceneSerije,this.getContext());
        preporucenoListaSerija.setAdapter(this.popisSerijaAdapter);
    }


}
