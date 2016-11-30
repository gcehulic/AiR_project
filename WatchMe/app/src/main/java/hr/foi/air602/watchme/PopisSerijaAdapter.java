package hr.foi.air602.watchme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Goran on 23.11.2016..
 */

public class PopisSerijaAdapter extends BaseAdapter {

    private ArrayList<Serija> serije;
    private Context context;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.serija_red, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
           holder = (ViewHolder) convertView.getTag();
        }

        Serija serija = (Serija) getItem(position);
        holder.naslov.setText(serija.getNaslov());
        holder.godina.setText(""+serija.getGodina()+".");

        String zanr = serija.getGenres();
        if(zanr!=null && !zanr.equals("")){
            if (zanr.contains("action"))
               holder.zanr_akcija.setVisibility(View.VISIBLE);
            if (zanr.contains("adventure"))
                holder.zanr_avantura.setVisibility(View.VISIBLE);
            if (zanr.contains("comedy"))
                holder.zanr_komedija.setVisibility(View.VISIBLE);
            if (zanr.contains("crime"))
                holder.zanr_krim.setVisibility(View.VISIBLE);
            if (zanr.contains("documentary"))
                holder.zanr_dokumentarni.setVisibility(View.VISIBLE);
            if (zanr.contains("drama"))
                holder.zanr_drama.setVisibility(View.VISIBLE);
            if (zanr.contains("family"))
                holder.zanr_obiteljska.setVisibility(View.VISIBLE);
            if (zanr.contains("fantasy"))
                holder.zanr_fantazija.setVisibility(View.VISIBLE);
            if (zanr.contains("science-fiction"))
                holder.zanr_sf.setVisibility(View.VISIBLE);
            if (zanr.contains("thriller"))
                holder.zanr_triler.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


    private class ViewHolder{
        private TextView naslov, godina;
        private TextView zanr_akcija, zanr_avantura, zanr_komedija, zanr_krim, zanr_dokumentarni, zanr_drama,
                zanr_obiteljska, zanr_fantazija, zanr_sf, zanr_triler;

        public ViewHolder(View v){
            this.naslov = (TextView) v.findViewById(R.id.serija_naslov);
            this.godina = (TextView) v.findViewById(R.id.serija_godina);

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
        }
    }


}
