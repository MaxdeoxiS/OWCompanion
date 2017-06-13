package com.example.maxime.overwatchstats.model;

public class HeroStats {
    private AverageStats avg;
    private OverallStats ovl;
    private SpecificStats spc;

    private String heroName;

    public HeroStats(String hero)
    {
        avg = new AverageStats();
        ovl = new OverallStats();
        spc = new SpecificStats();
        heroName = hero;
    }

    public AverageStats getAvg() {
        return avg;
    }

    public void setAvg(AverageStats avg) {
        this.avg = avg;
    }

    public OverallStats getOvl() {
        return ovl;
    }

    public void setOvl(OverallStats ovl) {
        this.ovl = ovl;
    }

    public SpecificStats getSpc() {
        return spc;
    }

    public void setSpc(SpecificStats spc) {
        this.spc = spc;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
