package com.example.maxime.overwatchstats.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Ability;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;

import java.util.List;

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

            List<Ability> abilities = hero.getAbilities();
            for (Ability ability : abilities) {
                ArrayMap<String, String> attributes = ability.getAvailableAttributes();

            }
        }

        return mView;
    }
}
