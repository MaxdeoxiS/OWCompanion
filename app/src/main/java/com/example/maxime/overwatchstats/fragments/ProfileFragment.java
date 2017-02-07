package com.example.maxime.overwatchstats.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maxime.overwatchstats.GetProfileStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.AchievementsActivity;

public class ProfileFragment extends Fragment {

    final String defaultBattleTag = "MaxdeoxiS-2292";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile, container, false);

        GetProfileStats profile = new GetProfileStats(mView, getActivity(), defaultBattleTag);
        profile.execute();

        final Button button_qp = (Button) mView.findViewById(R.id.profile_button_quickplay);
        button_qp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new QuickplayFragment())
                        .addToBackStack("QUICKPLAY")
                        .commit();
            }
        });

        final Button button_rk = (Button) mView.findViewById(R.id.profile_button_ranked);
        button_rk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new RankedFragment())
                        .addToBackStack("RANKED")
                        .commit();
            }
        });

        final Button button_ac = (Button) mView.findViewById(R.id.profile_button_achievements);
        button_ac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AchievementsActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }

}
