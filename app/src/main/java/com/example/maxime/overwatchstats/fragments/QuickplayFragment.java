package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxime.overwatchstats.GetHeroesStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.example.maxime.overwatchstats.model.HeroStats;

import java.util.ArrayList;
import java.util.HashMap;


public class QuickplayFragment extends Fragment implements
        GetHeroesStats.OnAsyncRequestComplete {

    ListView lv;
    HeroesAdapter adapter;
    HashMap<String, HeroStats> stats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile_quickplay, container, false);

        adapter = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>());
        lv = (ListView)     mView.findViewById(R.id.quickplay_list_heroes);
        lv.setAdapter(adapter);

        GetHeroesStats test = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter, "quickplay", this);
        test.execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String heroName = ((HeroItem)adapterView.getAdapter().getItem(i)).getName();
                HeroesStatsFragment heroesStatsFrgm = new HeroesStatsFragment();
                Bundle args = new Bundle();

                args.putSerializable("stats", stats.get(heroName));
                heroesStatsFrgm.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, heroesStatsFrgm)
                        .addToBackStack("HEROES_STATS")
                        .commit();
            }
        });

        return mView;
    }

    @Override
    public void asyncResponse(HashMap<String, HeroStats> stats)
    {
        this.stats = stats;
    }
}
