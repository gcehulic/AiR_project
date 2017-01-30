package hr.foi.air602.watchme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import hr.foi.air602.notification.service.MyFirebaseMessagingService;
import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.database.entities.UserFavorite;
import hr.foi.air602.watchme.strategies.ScheduledNotificationStrategy;

/**
 * Created by Goran on 23.11.2016..
 */

public class SeriesListAdapter extends BaseAdapter {

    private ArrayList<Series> serije;
    private Context context;
    private boolean mChecked;


    public SeriesListAdapter(ArrayList<Series> serije, Context context) {
        this.serije = serije;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.serije.size();
    }

    @Override
    public Object getItem(int position) {
        return this.serije.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.serija_red, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final Series series = (Series) getItem(position);
        holder.naslov.setText(series.getNaslov());
        holder.godina.setText("" + series.getGodina() + ".");

        holder.idSerije.setText(""+ series.getIdTrakt());
        holder.slug.setText(series.getSlug());
        holder.zanrovi.setText(series.getZanrovi());
        holder.emitiranje.setText(series.getEmitiranje());
        holder.mreza.setText(series.getMreza());

        final String naslovSerije = series.getNaslov().toString();
        String prvoSlovoNaslova = naslovSerije.substring(0, 1);

        ColorGenerator generator = ColorGenerator.MATERIAL;

        int boja = generator.getColor(naslovSerije);

        String zanr = series.getGenres();
        if (zanr != null && !zanr.equals("")) {
            if (zanr.contains("action")) {
                holder.zanrAkcija.setVisibility(View.VISIBLE);
                holder.zanrAkcija.setBackgroundColor(boja);
            } else
                holder.zanrAkcija.setVisibility(View.GONE);
            if (zanr.contains("adventure")) {
                holder.zanrAvantura.setVisibility(View.VISIBLE);
                holder.zanrAvantura.setBackgroundColor(boja);
            } else
                holder.zanrAvantura.setVisibility(View.GONE);
            if (zanr.contains("comedy")) {
                holder.zanrKomedija.setVisibility(View.VISIBLE);
                holder.zanrKomedija.setBackgroundColor(boja);
            } else
                holder.zanrKomedija.setVisibility(View.GONE);
            if (zanr.contains("crime")) {
                holder.zanrKrim.setVisibility(View.VISIBLE);
                holder.zanrKrim.setBackgroundColor(boja);
            } else
                holder.zanrKrim.setVisibility(View.GONE);
            if (zanr.contains("documentary")) {
                holder.zanrDokumentarni.setVisibility(View.VISIBLE);
                holder.zanrDokumentarni.setBackgroundColor(boja);
            } else
                holder.zanrDokumentarni.setVisibility(View.GONE);
            if (zanr.contains("drama")) {
                holder.zanrDrama.setVisibility(View.VISIBLE);
                holder.zanrDrama.setBackgroundColor(boja);
            } else
                holder.zanrDrama.setVisibility(View.GONE);
            if (zanr.contains("family")) {
                holder.zanrObiteljska.setVisibility(View.VISIBLE);
                holder.zanrObiteljska.setBackgroundColor(boja);
            } else
                holder.zanrObiteljska.setVisibility(View.GONE);
            if (zanr.contains("fantasy")) {
                holder.zanrFantazija.setVisibility(View.VISIBLE);
                holder.zanrFantazija.setBackgroundColor(boja);
            } else
                holder.zanrFantazija.setVisibility(View.GONE);
            if (zanr.contains("science-fiction")) {
                holder.zanrSf.setVisibility(View.VISIBLE);
                holder.zanrSf.setBackgroundColor(boja);
            } else
                holder.zanrSf.setVisibility(View.GONE);
            if (zanr.contains("thriller")) {
                holder.zanrTriler.setVisibility(View.VISIBLE);
                holder.zanrTriler.setBackgroundColor(boja);
            } else
                holder.zanrTriler.setVisibility(View.GONE);
            if (zanr.contains("reality")) {
                holder.zanrReality.setVisibility(View.VISIBLE);
                holder.zanrReality.setBackgroundColor(boja);
            } else
                holder.zanrReality.setVisibility(View.GONE);
            if (zanr.contains("animation")) {
                holder.zanrAnimirani.setVisibility(View.VISIBLE);
                holder.zanrAnimirani.setBackgroundColor(boja);
            } else
                holder.zanrAnimirani.setVisibility(View.GONE);
        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(prvoSlovoNaslova, boja, 100); // radius u px

        holder.prvoSlovoNaslova.setImageDrawable(drawable);

        UserAdapter userAdapter = new UserAdapter(context);
        UserFavoriteAdapter userFavoriteAdapter = new UserFavoriteAdapter(context);
        mChecked = userFavoriteAdapter.doesFavoriteExists(userAdapter.getUserFromSharedPrefs(),holder.idSerije.getText().toString());

        holder.odabirSerije.setChecked(mChecked);
        holder.odabirSerije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChecked = !mChecked;
                holder.odabirSerije.setChecked(mChecked);

                Log.d("WATCHME", "onClick: serijaID : "+holder.idSerije.getText());
                String serijaID = holder.idSerije.getText().toString();
                UserFavoriteAdapter userFavoriteAdapter = new UserFavoriteAdapter(context);
                UserAdapter userAdapter = new UserAdapter(context);
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(context);
                int korisnik = userAdapter.getUserFromSharedPrefs();
                if(holder.odabirSerije.isChecked()){
                    if(!userFavoriteAdapter.doesFavoriteExists(korisnik, serijaID)){
                        favoriteAdapter.insertFavorite(new Favorite(serijaID,holder.slug.getText().toString(),holder.zanrovi.getText().toString(),holder.emitiranje.getText().toString(),holder.mreza.getText().toString())); //PROMENITI
                        userFavoriteAdapter.insertUserFavorite(new UserFavorite(korisnik,serijaID));

                        Log.d("WATCHME", "onClick: oznaceno " + serijaID + " dodano u bazu" );


                    }

                } else {
                    if(userFavoriteAdapter.doesFavoriteExists(korisnik,serijaID)){
                        userFavoriteAdapter.deleteUserFavorite(new UserFavorite(korisnik,serijaID));
                        favoriteAdapter.deleteFavorite(new Favorite(serijaID,"","","",""));
                        Log.d("WATCHME", "onClick: odznaceno i obrisano");
                    }

                }
                ScheduledNotificationStrategy.getInstance(context.getApplicationContext()).updateList(favoriteAdapter.getAllFavorites());
                MyFirebaseMessagingService.getInstance().schedulingNotifs();
            }
        });

      return convertView;

    }




    private class ViewHolder{
        private TextView naslov, godina, idSerije;
        private TextView zanrAkcija, zanrAvantura, zanrKomedija, zanrKrim, zanrDokumentarni, zanrDrama,
                zanrObiteljska, zanrFantazija, zanrSf, zanrTriler, zanrReality, zanrAnimirani;
        private ImageView prvoSlovoNaslova;
        private CheckBox odabirSerije;
        private TextView slug, emitiranje, mreza, zanrovi;

        public ViewHolder(View v){
            this.naslov = (TextView) v.findViewById(R.id.serijaNaslov);
            this.godina = (TextView) v.findViewById(R.id.serijaGodina);
            this.odabirSerije = (CheckBox) v.findViewById(R.id.odabir);

            this.idSerije = (TextView) v.findViewById(R.id.idserije);

            this.zanrAkcija = (TextView) v.findViewById(R.id.akcija);
            this.zanrAvantura = (TextView) v.findViewById(R.id.avantura);
            this.zanrKomedija = (TextView) v.findViewById(R.id.komedija);
            this.zanrKrim = (TextView) v.findViewById(R.id.krim);
            this.zanrDokumentarni = (TextView) v.findViewById(R.id.dokumentarni);
            this.zanrDrama = (TextView) v.findViewById(R.id.drama);
            this.zanrObiteljska = (TextView) v.findViewById(R.id.obiteljska);
            this.zanrFantazija = (TextView) v.findViewById(R.id.fantazija);
            this.zanrSf = (TextView) v.findViewById(R.id.sf);
            this.zanrTriler = (TextView) v.findViewById(R.id.triler);
            this.zanrReality = (TextView) v.findViewById(R.id.reality);
            this.zanrAnimirani = (TextView) v.findViewById(R.id.animirani);

            this.prvoSlovoNaslova = (ImageView) v.findViewById(R.id.image_view);
            this.slug = (TextView) v.findViewById(R.id.slug);
            this.zanrovi = (TextView) v.findViewById(R.id.zanrovi);
            this.emitiranje = (TextView) v.findViewById(R.id.emitiranje);
            this.mreza = (TextView) v.findViewById(R.id.mreza);
        }
    }


}
