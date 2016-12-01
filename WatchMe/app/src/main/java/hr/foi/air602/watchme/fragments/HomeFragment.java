package hr.foi.air602.watchme.fragments;

import android.support.v4.app.Fragment;
import android.os.Build;
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

import hr.foi.air602.watchme.MainActivity;
import hr.foi.air602.watchme.PopisSerijaAdapter;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.Serija;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.DohvatSerijaAsyncTask;
import hr.foi.air602.watchme.listeners.SerijeDohvaceneListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class HomeFragment extends Fragment implements SerijeDohvaceneListener,AdapterView.OnItemClickListener {
    private TextView mTextView;
    public static ArrayList<Serija> dohvaceneSerije;
    private PopisSerijaAdapter popisSerijaAdapter;
    private ListView listaSerija;
    private int brojStranica = 1;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_spinner);
        mProgressBar.setVisibility(View.VISIBLE);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    private  void initialize(){

        dohvaceneSerije = new ArrayList<>();

        listaSerija = (ListView) this.getActivity().findViewById(R.id.home_lista_serija);

            if(Utilities.povezanost(getActivity().getApplicationContext())){
                String url = Utilities.izradaUrlSerije("trending",brojStranica);
                this.dohvatSerija(url);
                setListViewAdapter();
                listaSerija.setOnItemClickListener(this);
                listaSerija.setOnScrollListener(this.onScrollListener());

        } else {
                Toast.makeText(this.getContext(),"No connection",Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
        }

    }

    private void dohvatSerija(String url){
        new DohvatSerijaAsyncTask(this,this.getContext(),url).execute();
    }

    private void setListViewAdapter(){
        this.popisSerijaAdapter = new PopisSerijaAdapter(dohvaceneSerije,this.getContext());
        listaSerija.setAdapter(this.popisSerijaAdapter);
    }

    @Override
    public void serijeDohvacene(ArrayList<Serija> serije, int scroll) {
        HomeFragment.dohvaceneSerije.addAll(serije);
        for (Serija s:HomeFragment.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "serijeDohvacene: "+s.getNaslov()+" "+s.getGodina()+" "+ s.getGenres());
        }
        popisSerijaAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                        dohvatSerija(Utilities.izradaUrlSerije("trending",brojStranica));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        };
    }
}
