package com.example.maxime.overwatchstats.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Ability;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;
import com.example.maxime.overwatchstats.tools.OnSwipeTouchListener;

import java.util.Map;

public class HeroAbilitiesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.abilities_fragment, container, false);

        LinearLayout mLinearLayout = (LinearLayout) mView.findViewById(R.id.abilities_fragment_layout);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        if (getArguments() != null) {

            final String heroName = getArguments().getString("hero");
            Hero hero = heroes.findHeroByName(heroName);


         for (Ability ability : hero.getAbilities()) {
             ArrayMap<String, String> attributes = ability.getAvailableAttributes();
             for (Map.Entry<String, String> attr : attributes.entrySet()) {

                 final TextView rowTextView = new TextView(this.getContext());
                 final TextView rowTextView2 = new TextView(this.getContext());

                 rowTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                 rowTextView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                 rowTextView.setText(attr.getKey());
                 rowTextView2.setText(attr.getValue());

                 rowTextView.setTextColor(Color.RED);
                 rowTextView2.setTextColor(Color.BLACK);

                 mLinearLayout.addView(rowTextView);
                 mLinearLayout.addView(rowTextView2);
             }
         }


            mView.setOnTouchListener(new OnSwipeTouchListener(this.getContext()) {
                @Override
                public void onSwipeLeft() {
                    getFragmentManager().popBackStack("HEROES_MAIN", 0);
                }


                @Override
                public void onSwipeRight() {
                    HeroDescFragment frgm = new HeroDescFragment();
                    Bundle args = new Bundle();
                    args.putString("hero", heroName);
                    frgm.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, frgm)
                            .addToBackStack("HERO_DESC")
                            .commit();
                }
            });

        }


        return mView;
    }
}
