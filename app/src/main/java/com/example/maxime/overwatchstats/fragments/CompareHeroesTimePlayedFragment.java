package com.example.maxime.overwatchstats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maxime.overwatchstats.GetHeroesStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.HeroesStatsActivity;
import com.example.maxime.overwatchstats.adapters.HeroesAdapter;
import com.example.maxime.overwatchstats.model.HeroItem;
import com.example.maxime.overwatchstats.model.HeroStats;

import java.util.ArrayList;
import java.util.HashMap;


public class CompareHeroesTimePlayedFragment extends Fragment implements
        GetHeroesStats.OnAsyncRequestComplete {

    ListView lv;
    ListView lv2;
    HeroesAdapter adapter;
    HeroesAdapter adapter2;
    HashMap<String, HeroStats> stats;
    View clickSource;
    View touchSource;

    int offset = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_compare, container, false);

        adapter = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>(), "qp_hero_img_1", "qp_hero_bar", "list_heroes");
        adapter2 = new HeroesAdapter(this.getActivity(), new ArrayList<HeroItem>(), "qp_hero_img_1", "qp_hero_bar", "list_heroes");
        lv = (ListView)     mView.findViewById(R.id.quickplay_list_heroes1);
        lv.setAdapter(adapter);

        String mode = "quickplay";//this.getArguments().getString("mode");

        GetHeroesStats test = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter, mode, this);
        GetHeroesStats test2 = new GetHeroesStats(getActivity().getWindow().getDecorView().getRootView(), this.getActivity(), adapter2, mode, this, "Blackout-2716");
        test.execute();

        lv2 = (ListView)     mView.findViewById(R.id.quickplay_list_heroes2);
        lv2.setAdapter(adapter2);

        test2.execute();






        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(touchSource == null)
                    touchSource = v;

                if(v == touchSource) {
                    lv2.dispatchTouchEvent(event);
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        clickSource = v;
                        touchSource = null;
                    }
                }

                return false;
            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view == clickSource)
                    lv2.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop() + offset);
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HeroItem currentItem =  (HeroItem)adapterView.getAdapter().getItem(i);
                String heroName = currentItem.getName();
                float playTime = currentItem.getPlayTimeAsFloat();

                if(playTime > 0) {
                    Intent intent = new Intent(getContext(), HeroesStatsActivity.class);
                    intent.putExtra("stats", stats.get(heroName));
                    intent.putExtra("heroName", heroName);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Vous n'avez pas joué ce héros !", Toast.LENGTH_SHORT).show();
                }

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
