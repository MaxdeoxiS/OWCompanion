package com.example.maxime.overwatchstats.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxime.overwatchstats.GetAchievementsStats;
import com.example.maxime.overwatchstats.R;

public class AchievementsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_achievements, container, false);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        final String battleTag = preferences.getString("currentBattleTag", "");

        GetAchievementsStats achievements = new GetAchievementsStats(mView, getActivity(), battleTag);
        //achievements.execute();

        return mView;
    }


}
