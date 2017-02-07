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

import java.util.ArrayList;


public class RankedFragment extends Fragment {

    ListView lv;
    HeroesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile_ranked, container, false);

        adapter = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>());
        lv = (ListView)     mView.findViewById(R.id.ranked_list_heroes);
        lv.setAdapter(adapter);

        GetHeroesStats test = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter, "competitive");
        test.execute();

        return mView;
    }
}
