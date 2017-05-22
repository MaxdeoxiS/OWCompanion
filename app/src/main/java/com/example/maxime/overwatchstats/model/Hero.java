package com.example.maxime.overwatchstats.model;

import java.io.Serializable;
import java.util.List;

public class Hero implements Serializable{

    private String nickname;
    private String firstName;
    private String lastName;
    private String desc;
    private int age;

    private double x;
    private double y;

    private List<Ability> abilities;

    public Hero(String name, String firstName, String lastName, double x, double y, int age, String description, List<Ability> ab) {
        this.nickname = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.x = x;
        this.y = y;
        this.age = age;
        this.desc = description;
        this.abilities = ab;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDescription() {
        return desc;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public void setAbilities(List<Ability> ab) { this.abilities = ab; }
    public void addAbility(Ability a) { this.abilities.add(a); }
    public List<Ability> getAbilities() { return this.abilities; }

}
