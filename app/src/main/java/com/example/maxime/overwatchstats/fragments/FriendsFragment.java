package com.example.maxime.overwatchstats.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.FriendsAdapter;
import com.example.maxime.overwatchstats.model.Friend;
import com.example.maxime.overwatchstats.model.FriendDAO;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    FriendsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_friends, container, false);

        FriendDAO f = new FriendDAO(this.getContext());
        f.open();
        ArrayList<Friend> friends = f.getAllFriends();
        f.close();

        if (friends.size() > 0) {
            TextView t = (TextView)  mView.findViewById(R.id.label_no_friends);
            t.setVisibility(View.INVISIBLE);
            adapter = new FriendsAdapter(this.getActivity(), friends);
            Log.v("adapter:", adapter.getCount()+"");
            ListView lv = (ListView)     mView.findViewById(R.id.listview_friends);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    String battleTag = ((TextView) arg1).getText().toString();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("currentBattleTag", battleTag);
                    editor.apply();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new ProfileFragment())
                            .addToBackStack("PROFILE")
                            .commit();
                }
            });

        }

        return mView;
    }


}
