package com.example.maxime.overwatchstats.activities;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.TabsPagerAdapter;
import com.example.maxime.overwatchstats.model.HeroStats;

public class HeroesStatsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_stats);

        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);

        HeroStats stats = null;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             stats = (HeroStats) extras.getSerializable("stats");
        }

        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), this, stats);

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

    }
}
