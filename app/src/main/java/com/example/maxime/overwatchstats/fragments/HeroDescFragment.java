package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;

public class HeroDescFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.hero_description, container, false);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));
        if (getArguments() != null) {
            String heroName = getArguments().getString("hero");
            Hero hero = heroes.findHeroByName(heroName);

            TextView heroNameTV = (TextView) mView.findViewById(R.id.hero_name);
            String heroAbility = "";

        }

        return mView;
    }

}
