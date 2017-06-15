package com.example.maxime.overwatchstats.model;


import java.util.ArrayList;
import java.util.HashMap;

public class HeroOutput {

    private ArrayList<HeroItem> heroesItem;
    private HashMap<String, HeroStats> heroesStats;

    public HeroOutput(ArrayList<HeroItem> items, HashMap<String, HeroStats> stats) {
        this.heroesItem = items;
        this.heroesStats = stats;
    }

    public ArrayList<HeroItem> getHeroesItem() {
        return heroesItem;
    }

    public void setHeroesItem(ArrayList<HeroItem> heroesItem) {
        this.heroesItem = heroesItem;
    }

    public HashMap<String, HeroStats> getHeroesStats() {
        return heroesStats;
    }

    public void setHeroesStats(HashMap<String, HeroStats> heroesStats) {
        this.heroesStats = heroesStats;
    }
}
