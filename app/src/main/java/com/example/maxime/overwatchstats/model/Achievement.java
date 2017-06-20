package com.example.maxime.overwatchstats.model;

public class Achievement {

    public enum Type {
        special("special"),
        maps("maps"),
        support("support"),
        general("general"),
        tank("tank"),
        defense("defense"),
        offense("offense");

        private String name = "";

        Type(String name){this.name = name;}

        public String toString(){return name;}
    }

    private String title;
    private String description;
    private boolean isAchieved;
    private Type type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAchieved() {
        return isAchieved;
    }

    public void setAchieved(boolean achieved) {
        isAchieved = achieved;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
