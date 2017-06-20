package com.example.maxime.overwatchstats.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.maxime.overwatchstats.GetAchievementsStats;
import com.example.maxime.overwatchstats.R;

public class AchievementsFragment extends Fragment {

    ArrayAdapter adapter;
    ListView lv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_achievements, container, false);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        final String battleTag = preferences.getString("currentBattleTag", "");

        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.achievement_item);
        lv = (ListView)     mView.findViewById(R.id.achievements_list);
        lv.setAdapter(adapter);

        GetAchievementsStats achievements = new GetAchievementsStats(mView, getActivity(), battleTag, adapter);
        achievements.execute();

        return mView;
    }


}
