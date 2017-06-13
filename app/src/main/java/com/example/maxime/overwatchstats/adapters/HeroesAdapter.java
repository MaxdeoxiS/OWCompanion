package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HeroesAdapter extends ArrayAdapter<HeroItem> {
    public HeroesAdapter(Context context, ArrayList<HeroItem> heroes) {
        super(context, 0, heroes);
    }

    float maxWidth;
    int defWidth = 800;

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
        View heroTimeBar = (View) convertView.findViewById(R.id.qp_hero_bar);

        // Populate the data into the template view using the data object
        Picasso.with(getContext()).load(hero.imgUrl).into(heroImg);
        float width = 0;
        width = Float.parseFloat(hero.playTime);

        if (position == 0)
            maxWidth = width;

        heroTimeBar.setLayoutParams(new LinearLayout.LayoutParams(calculateWidth(maxWidth, width, defWidth), LinearLayout.LayoutParams.MATCH_PARENT));

        Log.v("tesssst", ""+ width);

        String nameCleaned = hero.name.toLowerCase().replaceAll(" |:", "").replaceAll("ú", "u").replaceAll("ö", "o");

        heroTimeBar.setBackgroundColor(Color.parseColor(this.getContext().getString(this.getContext().getResources().getIdentifier(nameCleaned, "string", getContext().getPackageName()))));

        // Return the completed view to render on screen
        return convertView;
    }

    private int calculateWidth(float maxWidth, float width, int defWidth) {

        return (int)((defWidth * width) / maxWidth);
    }

}