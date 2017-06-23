package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Achievement;

import java.util.ArrayList;

public class AchievementsAdapter extends ArrayAdapter<Achievement> {

    private ArrayList<Achievement> achvs;

    public AchievementsAdapter(Context context, ArrayList<Achievement> achvs) {
        super(context, 0, achvs);
        this.achvs = achvs;
    }


    @Override
    public int getCount() {
        return achvs.size();
    }

    @Override
    public Achievement getItem(int position) {
        return achvs.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Achievement ach = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.achievement_item, parent, false);
        }


        TextView title = (TextView) convertView.findViewById(R.id.achievement_item);
        TextView desc = (TextView) convertView.findViewById(R.id.achievement_desc);
        title.setText(ach.getTitle());
        desc.setText(ach.getDescription());
        title.setBackgroundColor(Color.RED);

        if(ach.isAchieved())
            title.setBackgroundColor(Color.GREEN);

        return convertView;
    }

}