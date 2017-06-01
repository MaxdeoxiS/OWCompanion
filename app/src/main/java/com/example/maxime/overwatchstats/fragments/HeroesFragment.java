package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Ability;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;

import java.util.Map;

public class HeroesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_heroes, container, false);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        TextView tv = (TextView) mView.findViewById(R.id.temp);
        String temp = "";

        for (Hero hero : heroes.getHeroes()) {
            for (Ability ability : hero.getAbilities()) {
                ArrayMap<String, String> attributes = ability.getAvailableAttributes();
                        for (Map.Entry<String, String> attr : attributes.entrySet()) {
                            temp += attr.getKey();
                            temp += attr.getValue();
                        }
            }
        }

        tv.setText(temp);

//        Bundle args = new Bundle();
//        args.putString("hero", (String) hero.getName());
//        HeroAbilitiesFragment frgm = new HeroAbilitiesFragment();
//        frgm.setArguments(args);

        return mView;
    }
}
