package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;

import java.util.ArrayList;

public class StatsAdapter extends ArrayAdapter<String> {

    private ArrayList<String> stats;
    private Context context;
    private String heroName;

    public StatsAdapter(Context context, ArrayList<String> stats, String heroName) {
        super(context, 0, stats);
        this.stats = stats;
        this.context = context;
        this.heroName = heroName;
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
        if(position > 5)
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
        String label = parseStatName(tmp[0], getContext());
        String stat = tmp[1];

        if (getItemViewType(position) == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_item_first, parent, false);
            }

            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.layout_stats_firsts);
            TextView statViewLabel = (TextView) convertView.findViewById(R.id.text_stats_first_label);
            TextView statViewStat = (TextView) convertView.findViewById(R.id.text_stats_first_stat);

            statViewLabel.setText(label);
            statViewStat.setText(stat);

            ll.setBackgroundColor(Color.parseColor(context.getString(context.getResources().getIdentifier(this.heroName, "string", context.getPackageName()))));


            return convertView;
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stats_item, parent, false);
        }


        TextView statView = (TextView) convertView.findViewById(R.id.text_stats_1);
        TextView statView2 = (TextView) convertView.findViewById(R.id.text_stats_2);
        statView.setText(label);
        statView2.setText(stat);

        return convertView;
    }

    protected String parseStatName(String statName, Context context) {
        return context.getString(context.getResources().getIdentifier(statName, "string", context.getPackageName()));
    }
}