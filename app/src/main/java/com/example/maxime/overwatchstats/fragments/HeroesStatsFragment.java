package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.SampleFragmentPagerAdapter;

public class HeroesStatsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.hero_stats, container, false);

        if (getArguments() != null) {
            //HeroStats stats = (HeroStats) getArguments().getSerializable("stats");
            //TextView tmp = (TextView) mView.findViewById(R.id.hero_stats);
            //tmp.setText(stats.getHeroName() + "" + stats.getAvg().getDragonblade_kills_average());
        }
        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return mView;
    }
}
