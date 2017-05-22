package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maxime.overwatchstats.R;

public class HeroDescFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.hero_description, container, false);

        if (getArguments() != null) {
            String heroId = getArguments().getString("id");

            TextView heroName = (TextView) mView.findViewById(R.id.hero_name);
            heroName.setText("Hero " + heroId);
        }

        return mView;
    }

}
