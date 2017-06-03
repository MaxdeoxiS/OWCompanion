package com.example.maxime.overwatchstats.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Ability;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;

import java.util.Map;

public class HeroAbilitiesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.abilities_fragment, container, false);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        if (getArguments() != null) {
            String heroName = getArguments().getString("hero");
            Hero hero = heroes.findHeroByName(heroName);
            Log.v("testt!!!!", heroName + "-" );


        TextView tv = (TextView) mView.findViewById(R.id.ability_temp);
        String temp = "";

         for (Ability ability : hero.getAbilities()) {
             ArrayMap<String, String> attributes = ability.getAvailableAttributes();
             for (Map.Entry<String, String> attr : attributes.entrySet()) {
                 temp += attr.getKey();
                 temp += attr.getValue();
             }
         }

        tv.setText(temp);

        }

        return mView;
    }
}
