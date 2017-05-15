package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Friend;
import com.example.maxime.overwatchstats.model.FriendDAO;

public class NewsFragment extends Fragment {

    final String defaultBattleTag = "MaxdeoxiS-2292";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mView = inflater.inflate(R.layout.activity_news, container, false);

        final FriendDAO f = new FriendDAO(this.getActivity());
        f.open();
        //f.emptyTable();
        Friend mySelf = f.select(1);
        f.close();


        TextView welcome = (TextView) mView.findViewById(R.id.text_welcome);

        if (null != mySelf) {
            welcome.setText("Bonjour " + mySelf.getUsername() + " ! ");
        } else {
            welcome.setText("Bonjour ! ");
        }


        return mView;
    }

}
