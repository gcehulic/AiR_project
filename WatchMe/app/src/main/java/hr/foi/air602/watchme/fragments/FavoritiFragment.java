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

import hr.foi.air602.watchme.PopisSerijaAdapter;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.Serija;
import hr.foi.air602.watchme.SerijaDetalji;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.DohvatSerijaPoIdAsyncTask;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.listeners.SerijeDohvacenePoIdListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class FavoritiFragment extends Fragment implements AdapterView.OnItemClickListener, SerijeDohvacenePoIdListener {

    private UserFavoriteAdapter userFavoriteAdapter = null;
    private UserAdapter userAdapter = null;
    private List<Favorite> favoriti = new ArrayList<>();
    private ListView listaSerija = null;
    private ProgressBar mProgressBar;
    public static ArrayList<Serija> dohvaceneSerije;
    public static PopisSerijaAdapter popisSerijaAdapter;
    private TextView nemaFavorita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pregled_layout, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_spinner);
        mProgressBar.getIndeterminateDrawable().setColorFilter(0xFF3F51B5, android.graphics.PorterDuff.Mode.MULTIPLY);
        nemaFavorita = (TextView) rootView.findViewById(R.id.nema_favorita);

        return rootView;
    }

    public FavoritiFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init();
    }
   private  void initialize(){

        dohvaceneSerije = new ArrayList<>();

        listaSerija = (ListView) this.getActivity().findViewById(R.id.pregled_lista_serija);

        if(Utilities.povezanost(getActivity().getApplicationContext())){

            this.favoriti = this.userFavoriteAdapter.getAllUserFavorites(this.userAdapter.getUserFromSharedPrefs());

            if(this.favoriti.size() > 0) {
                nemaFavorita.setVisibility(View.GONE);
                for (Favorite f : this.favoriti) {
                    Log.d("WATCHME", "onViewCreated: id:" + f.id + " slug:" + f.slug);
                    String url = Utilities.izradaUrlSerijePoId(f.id);
                    this.dohvatSerijaPoId(url);
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

    private void dohvatSerijaPoId(String url){
        new DohvatSerijaPoIdAsyncTask(this,this.getContext(),url).execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Serija serije = dohvaceneSerije.get(position);

        Intent i = new Intent(getActivity(), SerijaDetalji.class);
        i.putExtra("naslov",serije.getNaslov());
        i.putExtra("godina",serije.getGodina());
        i.putExtra("zanrovi",serije.getGenres());
        i.putExtra("trailer", serije.getTrailer());
        i.putExtra("opis", serije.getOpis());

        startActivity(i);
    }

    @Override
    public void serijeDohvacenePoId(Serija serija) {
        FavoritiFragment.dohvaceneSerije.add(serija);
        for (Serija s: PocetnaFragment.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "serijeDohvacene: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        popisSerijaAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    private void setListViewAdapter(){
        this.popisSerijaAdapter = new PopisSerijaAdapter(dohvaceneSerije,this.getContext());
        listaSerija.setAdapter(this.popisSerijaAdapter);

    }

    public void init() {
        if(Utilities.povezanost(getActivity().getApplicationContext())) {
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
            popisSerijaAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
        }
        else{
            //
        }
    }


}

