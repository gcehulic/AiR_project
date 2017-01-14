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
import android.widget.TextView;
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
    private ListView preporucenoListaSerija=null;
    private TextView nemaPreporuka;
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

        preporucenoListaSerija = (ListView) this.getActivity().findViewById(R.id.preporuceno_lista_serija);
        String genres = favoriteAdapter.getRecommendedGenres();
        if (Utilities.povezanost(getActivity().getApplicationContext())) {
            if(!genres.equals("")) {
                preporucenoListaSerija.setVisibility(View.VISIBLE);
                String url = Utilities.izradaUrlSerijePreporuceno("trending", genres);
                this.dohvatSerija(url);
                setListViewAdapter();
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
            //Toast.makeText(this.getContext(), "Niste spojeni na internet", Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(View.GONE);
            internetGreska.setVisibility(View.GONE);
            nemaPreporuka.setVisibility(View.GONE);
            //preporucenoListaSerija.setBackgroundColor(Color.WHITE);
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
    }

    private void setListViewAdapter(){
        this.popisSerijaAdapter = new PopisSerijaAdapter(dohvaceneSerije,this.getContext());
        preporucenoListaSerija.setAdapter(this.popisSerijaAdapter);
    }


}
