package hr.foi.air602.watchme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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


        return convertView;
    }


    private class ViewHolder{
        private TextView naslov, godina;

        public ViewHolder(View v){
            this.naslov = (TextView) v.findViewById(R.id.serija_naslov);
            this.godina = (TextView) v.findViewById(R.id.serija_godina);
        }
    }


}
