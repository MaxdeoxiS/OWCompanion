package com.example.maxime.overwatchstats.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.model.Heroes;

public class HeroesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mView = inflater.inflate(R.layout.fragment_heroes, container, false);

        Heroes heroes = new Heroes(this.getResources().openRawResource(R.raw.heroes));

        return mView;
    }
}
