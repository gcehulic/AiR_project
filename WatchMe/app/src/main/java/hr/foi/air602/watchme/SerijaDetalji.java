package hr.foi.air602.watchme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONObject;

/**
 * Created by markopc on 12/1/2016.
 */

public class SerijaDetalji extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener  {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView youTubePrikaz;
    private ProgressBar mProgressBar;

    Serija serija;
    private  TextView godina, naslov, zanrovi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        youTubePrikaz = (YouTubePlayerView) findViewById(R.id.player_view);
        youTubePrikaz.initialize(Utilities.YOUTUBE_API_KEY, this);

        godina = (TextView) findViewById(R.id.godina); godina.setOnClickListener(this);
        naslov = (TextView) findViewById(R.id.naslovSerije); naslov.setOnClickListener(this);
        zanrovi = (TextView) findViewById(R.id.zanrovi); zanrovi.setOnClickListener(this);


        getIntentPodaci();

        setPodaci();

    }



    private void setPodaci() {
        naslov.setText(serija.getNaslov()+" ");
        godina.setText(serija.getGodina()+" ");
        zanrovi.setText(serija.getGenres());



    }
    private void getIntentPodaci() {

     serija = new Serija();

     serija.setNaslov(getIntent().getStringExtra("naslov"));
     serija.setGodina(getIntent().getIntExtra("godina",2013));
        serija.setGenres(getIntent().getStringExtra("zanrovi"));
        serija.setTrailer(getIntent().getStringExtra("trailer"));


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        if (!wasRestored) {
            player.cueVideo(serija.getTrailer().split("=")[1]);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(" error initializing YouTubePlayer", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
