package com.example.maxime.overwatchstats.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.maxime.overwatchstats.GetProfileStats;
import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.activities.AchievementsActivity;
import com.example.maxime.overwatchstats.model.FriendDAO;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.activity_profile, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        final String battleTag = preferences.getString("currentBattleTag", "");

        GetProfileStats profile = new GetProfileStats(mView, getActivity(), battleTag);
        profile.execute();

        final Button button_remove_friend = (Button) mView.findViewById(R.id.player_searched_button);


        button_remove_friend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FriendDAO f = new FriendDAO(getContext());
                f.open();
                boolean insert_remove = f.delete(battleTag);
                f.close();
                CharSequence text = (insert_remove) ? getString(R.string.friend_deleted) : getString(R.string.cant_delete_friend);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getContext(), text, duration);
                toast.show();
            }
        });

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
