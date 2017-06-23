package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.maxime.overwatchstats.fragments.StatsPageFragment;
import com.example.maxime.overwatchstats.model.HeroStats;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Héro", "Moyenne", "Général" };
    private Context context;
    private HeroStats stats;
    private String heroName;

    public TabsPagerAdapter(FragmentManager fm, Context context, HeroStats stats, String heroName) {
        super(fm);
        this.context = context;
        this.stats = stats;
        this.heroName = heroName;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return StatsPageFragment.newInstance(position + 1, this.stats, this.heroName);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}