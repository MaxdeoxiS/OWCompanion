package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

    private ArrayList<String> stats;

    public MyAdapter(Context context, ArrayList<String> stats) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        String stat = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_item, parent, false);
        }
        // Lookup view for data population
        TextView statView = (TextView) convertView.findViewById(R.id.text_stats);
        statView.setText(stat);
        Log.v("test!!", stat);


        return convertView;
    }
}