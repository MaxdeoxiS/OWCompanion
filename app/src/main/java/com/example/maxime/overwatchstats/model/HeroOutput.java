package com.example.maxime.overwatchstats.model;


import java.util.ArrayList;

public class HeroOutput {

    private ArrayList<HeroItem> heroesItem;
    private ArrayList<HeroStats> heroesStats;

    public HeroOutput(ArrayList<HeroItem> items, ArrayList<HeroStats> stats) {
        this.heroesItem = items;
        this.heroesStats = stats;
    }

    public ArrayList<HeroItem> getHeroesItem() {
        return heroesItem;
    }

    public void setHeroesItem(ArrayList<HeroItem> heroesItem) {
        this.heroesItem = heroesItem;
    }

    public ArrayList<HeroStats> getHeroesStats() {
        return heroesStats;
    }

    public void setHeroesStats(ArrayList<HeroStats> heroesStats) {
        this.heroesStats = heroesStats;
    }
}
