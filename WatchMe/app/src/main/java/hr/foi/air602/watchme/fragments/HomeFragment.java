package hr.foi.air602.watchme.fragments;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);

        mTextView = (TextView)rootView.findViewById(R.id.homeLayoutTextView);


        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Toast.makeText(getContext(), "Click!!", Toast.LENGTH_LONG).show();
                }
            }
        });

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
            String url = "https://api.trakt.tv/shows/trending";
            new DohvatSerijaAsyncTask(this,this.getContext(),url).execute();
            setListViewAdapter();
            listaSerija.setOnItemClickListener(this);

        } else {
            Toast.makeText(this.getContext(),"No connection",Toast.LENGTH_LONG).show();
        }

    }

    private void setListViewAdapter(){
        this.popisSerijaAdapter = new PopisSerijaAdapter(dohvaceneSerije,this.getContext());
        listaSerija.setAdapter(this.popisSerijaAdapter);
    }

    @Override
    public void serijeDohvacene(ArrayList<Serija> serije, int scroll) {
        HomeFragment.dohvaceneSerije.addAll(serije);
        for (Serija s:HomeFragment.dohvaceneSerije) {
            Log.d("HOMEFRAGMENT", "serijeDohvacene: "+s.getNaslov()+" "+s.getGodina()+" godina");
        }
        popisSerijaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
