package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;

import java.util.ArrayList;

public class StatsAdapter extends ArrayAdapter<String> {

    private ArrayList<String> stats;

    public StatsAdapter(Context context, ArrayList<String> stats) {
        super(context, 0, stats);
        this.stats = stats;
    }


    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public String getItem(int position) {
        return stats.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(position > 4)
            return 1;
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        String[] tmp = getItem(position).split(":");
        String label = tmp[0];
        String stat = tmp[1];

        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_item_first, parent, false);
            }

            TextView statViewLabel = (TextView) convertView.findViewById(R.id.text_stats_first_label);
            TextView statViewStat = (TextView) convertView.findViewById(R.id.text_stats_first_stat);

            statViewLabel.setText(label);
            statViewStat.setText(stat);

            return convertView;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_item, parent, false);
        }


        TextView statView = (TextView) convertView.findViewById(R.id.text_stats);
        statView.setText(label + " : " + stat);

        return convertView;
    }
}