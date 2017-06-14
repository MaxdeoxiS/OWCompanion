package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maxime.overwatchstats.GetHeroesStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.example.maxime.overwatchstats.model.HeroStats;

import java.util.ArrayList;
import java.util.HashMap;


public class RankedFragment extends Fragment implements
        GetHeroesStats.OnAsyncRequestComplete  {

    ListView lv;
    HeroesAdapter adapter;
    HashMap<String, HeroStats> stats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile_ranked, container, false);

        adapter = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>());
        lv = (ListView)     mView.findViewById(R.id.ranked_list_heroes);
        lv.setAdapter(adapter);

        GetHeroesStats test = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter, "competitive", this);
        test.execute();

        return mView;
    }

    @Override
    public void asyncResponse(HashMap<String, HeroStats> stats)
    {
        this.stats = stats;
    }
}
