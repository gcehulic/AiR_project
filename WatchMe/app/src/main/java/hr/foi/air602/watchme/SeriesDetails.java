package hr.foi.air602.watchme;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by markopc on 12/1/2016.
 */

public class SeriesDetails extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener  {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView youTubePrikaz;
    private ImageView youTubeGreska;

    Series series;
    private  TextView godina, naslov, opisFilma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        youTubePrikaz = (YouTubePlayerView) findViewById(R.id.player_view);
        youTubePrikaz.initialize(Utilities.YOUTUBE_API_KEY, this);

        godina = (TextView) findViewById(R.id.godina); godina.setOnClickListener(this);
        naslov = (TextView) findViewById(R.id.naslovSerije); naslov.setOnClickListener(this);
        opisFilma = (TextView) findViewById(R.id.tvOpis); opisFilma.setOnClickListener(this);

        youTubeGreska = (ImageView) findViewById(R.id.youtubeSlika);

        getIntentData();

        setData();

    }

    private void setData() {
        naslov.setText(series.getNaslov()+" ");
        godina.setText(series.getGodina()+" ");
        opisFilma.setText(series.getOpis());


        TextView t_akcija,t_komedija, t_drama,t_avatura ,t_s_f,t_krim, t_dokumentarac,
                t_obiteljska, t_fantazija, t_triler, t_reality, t_animirani;
        t_akcija = (TextView) findViewById(R.id.akcija);
        t_komedija = (TextView) findViewById(R.id.komedija);
        t_avatura = (TextView) findViewById(R.id.avantura);
        t_s_f = (TextView) findViewById(R.id.s_f);
        t_dokumentarac = (TextView) findViewById(R.id.dokumentarni);
        t_drama = (TextView) findViewById(R.id.drama);
        t_krim=(TextView)findViewById(R.id.krimi);
        t_obiteljska= (TextView) findViewById(R.id.obiteljski);
        t_fantazija = (TextView) findViewById(R.id.fantazija);
        t_triler = (TextView) findViewById(R.id.triler);
        t_reality = (TextView) findViewById(R.id.reality);
        t_animirani = (TextView) findViewById(R.id.animirani);

        String zanrovi = series.getGenres();
        if(zanrovi!=null && !zanrovi.equals("")){
            if (zanrovi.contains("action"))
                t_akcija.setVisibility(View.VISIBLE);
            if (zanrovi.contains("adventure"))
                t_avatura.setVisibility(View.VISIBLE);
            if (zanrovi.contains("comedy"))
                t_komedija.setVisibility(View.VISIBLE);
            if (zanrovi.contains("crime"))
                t_krim.setVisibility(View.VISIBLE);
            if (zanrovi.contains("documentary"))
                t_dokumentarac.setVisibility(View.VISIBLE);
            if (zanrovi.contains("drama"))
                t_drama.setVisibility(View.VISIBLE);
            if (zanrovi.contains("family"))
                t_obiteljska.setVisibility(View.VISIBLE);
            if (zanrovi.contains("fantasy"))
                t_fantazija.setVisibility(View.VISIBLE);
            if (zanrovi.contains("science-fiction"))
                t_s_f.setVisibility(View.VISIBLE);
            if (zanrovi.contains("thriller"))
                t_triler.setVisibility(View.VISIBLE);
            if (zanrovi.contains("reality"))
                t_reality.setVisibility(View.VISIBLE);
            if (zanrovi.contains("animation"))
                t_animirani.setVisibility(View.VISIBLE);


        }


    }

    private void getIntentData() {

        series = new Series();

        series.setNaslov(getIntent().getStringExtra("naslov"));
        series.setGodina(getIntent().getIntExtra("godina",2013));
        series.setGenres(getIntent().getStringExtra("zanrovi"));
        series.setTrailer(getIntent().getStringExtra("trailer"));
        series.setOpis(getIntent().getStringExtra("opis"));

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        if (!wasRestored) {

            try {
                player.cueVideo(series.getTrailer().split("=")[1]);
            }catch (Exception e){
                youTubePrikaz.setVisibility(View.GONE);
                youTubeGreska.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("Greška s YouTubePlayer-om", errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
