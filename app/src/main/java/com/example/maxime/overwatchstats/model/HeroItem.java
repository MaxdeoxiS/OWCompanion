package com.example.maxime.overwatchstats.model;

public class HeroItem {
    public String name;
    public String playTime;
    public String imgUrl;

    public HeroItem(String n, String p, String i) {
        this.name = n;
        this.playTime = p;
        this.imgUrl = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPlayTimeAsFloat() {
        return Float.parseFloat(playTime);
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
