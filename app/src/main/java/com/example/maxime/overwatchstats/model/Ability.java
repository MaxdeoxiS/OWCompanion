package com.example.maxime.overwatchstats.model;

import java.io.Serializable;

public class Ability implements Serializable {

    private String name;
    private String hero;
    private String type;
    private String speed;
    private String ammo;
    private String rate;
    private String reload;
    private String damage;
    private String cooldown;
    private String range;
    private String headshot;
    private String more;

    public Ability() {
    }

    public Ability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAmmo() {
        return ammo;
    }

    public void setAmmo(String ammo) {
        this.ammo = ammo;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReload() {
        return reload;
    }

    public void setReload(String reload) {
        this.reload = reload;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

}
