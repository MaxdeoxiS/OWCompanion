package com.example.maxime.overwatchstats.activities;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.maxime.overwatchstats.R;
import com.example.maxime.overwatchstats.adapters.TabsPagerAdapter;
import com.example.maxime.overwatchstats.model.HeroStats;

public class HeroesStatsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_stats);

        Context c = HeroesStatsActivity.this;

        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        ImageView heroImg = (ImageView) findViewById(R.id.img_hero_stats);

        String heroName = "";
        HeroStats stats = null;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             stats = (HeroStats) extras.getSerializable("stats");
             heroName = extras.getString("heroName");
        }

        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), this, stats, heroName);
        tabs.setSelectedTabIndicatorColor(Color.parseColor(c.getString(c.getResources().getIdentifier(heroName, "string", c.getPackageName()))));

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        heroImg.setImageBitmap(BitmapFactory.decodeResource(
                c.getResources(),c.getResources().getIdentifier(heroName, "drawable", c.getPackageName())));

    }

    protected String parseStatName(String statName, Context context) {
        return context.getString(context.getResources().getIdentifier(statName, "string", context.getPackageName()));
    }
}
