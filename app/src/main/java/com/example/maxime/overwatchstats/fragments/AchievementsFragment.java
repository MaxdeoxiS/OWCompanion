package com.example.maxime.overwatchstats.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.GetAchievementsStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.AchievementsAdapter;
import com.example.maxime.overwatchstats.model.Achievement;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    AchievementsAdapter adapter;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_achievements, container, false);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        final String battleTag = preferences.getString("currentBattleTag", "");

        adapter = new AchievementsAdapter(this.getActivity(), new ArrayList<Achievement>());

        lv = (ListView)     mView.findViewById(R.id.achievements_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView desc = (TextView) view.findViewById(R.id.achievement_desc);

                if ( desc.getVisibility() == View.GONE)
                {
                    desc.setVisibility(View.VISIBLE);
                }
                else
                {
                    desc.setVisibility(View.GONE);
                }
            }
        });

        GetAchievementsStats achievements = new GetAchievementsStats(mView, getActivity(), battleTag, adapter);
        achievements.execute();

        return mView;
    }


}
