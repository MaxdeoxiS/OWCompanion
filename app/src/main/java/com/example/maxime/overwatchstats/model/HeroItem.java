package com.example.maxime.overwatchstats.model;

public class HeroItem {
    public String name;
    public String playTime;
    public int percentage;

    public HeroItem(String n, String p, long pr) {
        this.name = n;
        this.playTime = p;
        this.percentage = (int)(100*pr);
    }

}
