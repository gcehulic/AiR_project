package hr.foi.air602.watchme.fragments;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import hr.foi.air602.watchme.MainActivity;
import hr.foi.air602.watchme.R;
import hr.foi.air602.watchme.Utilities;
import hr.foi.air602.watchme.async_tasks.DohvatSerijaAsyncTask;
import hr.foi.air602.watchme.listeners.SerijeDohvaceneListener;

/**
 * Created by markopc on 11/2/2016.
 */

public class HomeFragment extends Fragment implements SerijeDohvaceneListener {
    private TextView mTextView;

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

        if(Utilities.povezanost(getActivity().getApplicationContext())){
            String url = "https://api.trakt.tv/shows/trending";
            new DohvatSerijaAsyncTask(this,this.getContext(),url).execute();
        } else {
            Toast.makeText(this.getContext(),"No connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void serijeDohvacene(String json, int scroll) {
        Log.d("HOMEFRAGMENT", "serijeDohvacene: "+json);
    }
}
