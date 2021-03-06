package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.StatsAdapter;
import com.example.maxime.overwatchstats.model.HeroStats;
import com.example.maxime.overwatchstats.model.Stats;

import java.util.ArrayList;

public class StatsPageFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_STATS = "stats";
    public static final String ARG_HERO = "heroName";

    private int mPage;
    private String heroName;
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<String> stats;

    public static StatsPageFragment newInstance(int page, HeroStats stats, String heroName) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_HERO, heroName);
        switch(page) {
            case 1:
                args.putSerializable(ARG_STATS, stats.getSpc());
                break;
            case 2:
                args.putSerializable(ARG_STATS, stats.getAvg());
                break;
            case 3:
                args.putSerializable(ARG_STATS, stats.getOvl());
                break;
            default:
                break;
        }

        StatsPageFragment fragment = new StatsPageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        heroName = getArguments().getString(ARG_HERO);
        Stats mStats = (Stats) (getArguments().getSerializable(ARG_STATS));
        stats = mStats.getAvailableStats();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);


        StatsAdapter itemsAdapter =
                new StatsAdapter(this.getContext(), stats, heroName);

        GridView list = (GridView) view.findViewById(R.id.listview_stats);

        list.setAdapter(itemsAdapter);

        return view;
    }
}
