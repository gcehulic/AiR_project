package hr.foi.air602.watchme;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.database.FavoriteAdapter;
import hr.foi.air602.watchme.database.UserAdapter;
import hr.foi.air602.watchme.database.UserFavoriteAdapter;
import hr.foi.air602.watchme.database.entities.Favorite;
import hr.foi.air602.watchme.database.entities.User;
import hr.foi.air602.watchme.database.entities.UserFavorite;
import hr.foi.air602.watchme.fragments.PregledFragment;

/**
 * Created by Goran on 23.11.2016..
 */

public class PopisSerijaAdapter extends BaseAdapter {

    private ArrayList<Serija> serije;
    private Context context;
    private boolean mChecked;


    public PopisSerijaAdapter(ArrayList<Serija> serije, Context context) {
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


        final Serija serija = (Serija) getItem(position);
        holder.naslov.setText(serija.getNaslov());
        holder.godina.setText("" + serija.getGodina() + ".");

        final String naslov_serije = serija.getNaslov().toString();
        String prvo_slovo_naslova = naslov_serije.substring(0, 1);

        ColorGenerator generator = ColorGenerator.MATERIAL;

        int boja = generator.getColor(naslov_serije);

        String zanr = serija.getGenres();
        if (zanr != null && !zanr.equals("")) {
            if (zanr.contains("action")) {
                holder.zanr_akcija.setVisibility(View.VISIBLE);
                holder.zanr_akcija.setBackgroundColor(boja);
            } else
                holder.zanr_akcija.setVisibility(View.GONE);
            if (zanr.contains("adventure")) {
                holder.zanr_avantura.setVisibility(View.VISIBLE);
                holder.zanr_avantura.setBackgroundColor(boja);
            } else
                holder.zanr_avantura.setVisibility(View.GONE);
            if (zanr.contains("comedy")) {
                holder.zanr_komedija.setVisibility(View.VISIBLE);
                holder.zanr_komedija.setBackgroundColor(boja);
            } else
                holder.zanr_komedija.setVisibility(View.GONE);
            if (zanr.contains("crime")) {
                holder.zanr_krim.setVisibility(View.VISIBLE);
                holder.zanr_krim.setBackgroundColor(boja);
            } else
                holder.zanr_krim.setVisibility(View.GONE);
            if (zanr.contains("documentary")) {
                holder.zanr_dokumentarni.setVisibility(View.VISIBLE);
                holder.zanr_dokumentarni.setBackgroundColor(boja);
            } else
                holder.zanr_dokumentarni.setVisibility(View.GONE);
            if (zanr.contains("drama")) {
                holder.zanr_drama.setVisibility(View.VISIBLE);
                holder.zanr_drama.setBackgroundColor(boja);
            } else
                holder.zanr_drama.setVisibility(View.GONE);
            if (zanr.contains("family")) {
                holder.zanr_obiteljska.setVisibility(View.VISIBLE);
                holder.zanr_obiteljska.setBackgroundColor(boja);
            } else
                holder.zanr_obiteljska.setVisibility(View.GONE);
            if (zanr.contains("fantasy")) {
                holder.zanr_fantazija.setVisibility(View.VISIBLE);
                holder.zanr_fantazija.setBackgroundColor(boja);
            } else
                holder.zanr_fantazija.setVisibility(View.GONE);
            if (zanr.contains("science-fiction")) {
                holder.zanr_sf.setVisibility(View.VISIBLE);
                holder.zanr_sf.setBackgroundColor(boja);
            } else
                holder.zanr_sf.setVisibility(View.GONE);
            if (zanr.contains("thriller")) {
                holder.zanr_triler.setVisibility(View.VISIBLE);
                holder.zanr_triler.setBackgroundColor(boja);
            } else
                holder.zanr_triler.setVisibility(View.GONE);
            if (zanr.contains("reality")) {
                holder.zanr_reality.setVisibility(View.VISIBLE);
                holder.zanr_reality.setBackgroundColor(boja);
            } else
                holder.zanr_reality.setVisibility(View.GONE);
            if (zanr.contains("animation")) {
                holder.zanr_animirani.setVisibility(View.VISIBLE);
                holder.zanr_animirani.setBackgroundColor(boja);
            } else
                holder.zanr_animirani.setVisibility(View.GONE);
        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(prvo_slovo_naslova, boja, 100); // radius u px

        holder.prvo_slovo_naslova.setImageDrawable(drawable);
        mChecked = false;
        holder.odabirSerije.setChecked(mChecked);
        holder.odabirSerije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChecked = !mChecked;
                holder.odabirSerije.setChecked(mChecked);

                //db.serija.update(id, mChecked);
            }
        });

      return convertView;

    }




    private class ViewHolder{
        private TextView naslov, godina;
        private TextView zanr_akcija, zanr_avantura, zanr_komedija, zanr_krim, zanr_dokumentarni, zanr_drama,
                zanr_obiteljska, zanr_fantazija, zanr_sf, zanr_triler, zanr_reality, zanr_animirani;
        private ImageView prvo_slovo_naslova;
        private CheckBox odabirSerije;

        public ViewHolder(View v){
            this.naslov = (TextView) v.findViewById(R.id.serija_naslov);
            this.godina = (TextView) v.findViewById(R.id.serija_godina);
            this.odabirSerije = (CheckBox) v.findViewById(R.id.odabir);

            this.zanr_akcija = (TextView) v.findViewById(R.id.akcija);
            this.zanr_avantura = (TextView) v.findViewById(R.id.avantura);
            this.zanr_komedija = (TextView) v.findViewById(R.id.komedija);
            this.zanr_krim = (TextView) v.findViewById(R.id.krim);
            this.zanr_dokumentarni = (TextView) v.findViewById(R.id.dokumentarni);
            this.zanr_drama = (TextView) v.findViewById(R.id.drama);
            this.zanr_obiteljska = (TextView) v.findViewById(R.id.obiteljska);
            this.zanr_fantazija = (TextView) v.findViewById(R.id.fantazija);
            this.zanr_sf = (TextView) v.findViewById(R.id.sf);
            this.zanr_triler = (TextView) v.findViewById(R.id.triler);
            this.zanr_reality = (TextView) v.findViewById(R.id.reality);
            this.zanr_animirani = (TextView) v.findViewById(R.id.animirani);

            this.prvo_slovo_naslova = (ImageView) v.findViewById(R.id.image_view);

        }
    }


}
