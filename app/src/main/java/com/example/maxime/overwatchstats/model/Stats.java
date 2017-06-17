package com.example.maxime.overwatchstats.model;


import android.content.Context;

import java.util.ArrayList;

public abstract class Stats {

    protected Context context;

    abstract public ArrayList<String> getAvailableStats();

    protected String parseStatName(String statName) {
        return context.getString(context.getResources().getIdentifier(statName, "string", context.getPackageName()));
    }
}
