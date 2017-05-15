package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeroesAdapter extends ArrayAdapter<HeroItem> {
    public HeroesAdapter(Context context, ArrayList<HeroItem> heroes) {
        super(context, 0, heroes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        HeroItem hero = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_heroes, parent, false);
        }
        // Lookup view for data population

        ImageView heroImg = (ImageView) convertView.findViewById(R.id.qp_hero_img_1);
        TextView heroName = (TextView) convertView.findViewById(R.id.qp_hero_text_1);
        TextView heroPlayTime = (TextView) convertView.findViewById(R.id.qp_hero_text_2);

        // Populate the data into the template view using the data object
        Picasso.with(getContext()).load(hero.imgUrl).into(heroImg);
        heroName.setText(hero.name);
        heroPlayTime.setText(hero.playTime);
        // Return the completed view to render on screen
        return convertView;
    }

}