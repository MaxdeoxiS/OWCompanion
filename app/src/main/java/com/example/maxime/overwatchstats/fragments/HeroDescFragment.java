package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.MapsActivity;
import com.example.maxime.overwatchstats.model.Hero;
import com.example.maxime.overwatchstats.model.Heroes;
import com.example.maxime.overwatchstats.tools.OnSwipeTouchListener;

public class HeroDescFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.hero_description, container, false);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        if (getArguments() != null) {
            final String heroName = getArguments().getString("hero");
            Hero hero = heroes.findHeroByName(heroName);

            TextView heroNameTV = (TextView) mView.findViewById(R.id.hero_name);
            TextView heroDescTV = (TextView) mView.findViewById(R.id.hero_desc);
            heroDescTV.setMovementMethod(new ScrollingMovementMethod());

            String heroDesc = hero.getDescription();

            heroNameTV.setText(heroName);
            heroDescTV.setText(heroDesc);

            if (!(getActivity() instanceof MapsActivity)) {

                mView.setOnTouchListener(new OnSwipeTouchListener(this.getContext()) {
                    @Override
                    public void onSwipeRight() {
                        getFragmentManager().popBackStack("HEROES_MAIN", 0);
                    }

                    @Override
                    public void onSwipeLeft() {
                        HeroAbilitiesFragment frgm = new HeroAbilitiesFragment();
                        Bundle args = new Bundle();
                        args.putString("hero", heroName);
                        frgm.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, frgm)
                                .addToBackStack("HERO_ABILITIES")
                                .commit();
                    }
                });
            }

        }

        return mView;
    }

}
