package com.example.maxime.overwatchstats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Friend;

import java.util.ArrayList;

public class FriendsAdapter extends ArrayAdapter<Friend> {
    public FriendsAdapter(Context context, ArrayList<Friend> friends) {
        super(context, 0, friends);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_friends, parent, false);
        }
        // Lookup view for data population
        TextView battleTag = (TextView) convertView.findViewById(R.id.friend_battleTag);

        // Populate the data into the template view using the data object
        battleTag.setText(friend.getBattleTag());

        return convertView;
    }
}