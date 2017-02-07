package com.example.maxime.overwatchstats.model;


public class Profile {
    private String username;
    private String fullUsername;
    private String level;
    private int rank;
    private int time_qp;
    private int time_ranked;
    private int time_total;

    private String img_rank;
    private String img_stars;
    private String img_lvl;

    private String avatar;

    public Profile() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullUsername() {
        return fullUsername;
    }

    public void setFullUsername(String fullUsername) {
        this.fullUsername = fullUsername;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String lvl) {
        if (lvl.length() == 3)
            lvl = lvl.substring(1);
        if (lvl.length() == 4)
            lvl = lvl.substring(2);

        this.level = lvl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getTime_qp() {
        return time_qp;
    }

    public void setTime_qp(int time_qp) {
        this.time_qp = time_qp;
    }

    public int getTime_ranked() {
        return time_ranked;
    }

    public void setTime_ranked(int time_ranked) {
        this.time_ranked = time_ranked;
    }

    public int getTime_total() {
        return time_total;
    }

    public void setTime_total(int time_total) {
        this.time_total = time_total;
    }

    public String getImg_rank() {
        return img_rank;
    }

    public void setImg_rank(String img_rank) {
        this.img_rank = img_rank;
    }

    public String getImg_stars() {
        return img_stars;
    }

    public void setImg_stars(String img_stars) {
        this.img_stars = img_stars;
    }

    public String getImg_lvl() {
        return img_lvl;
    }

    public void setImg_lvl(String img_lvl) {
        this.img_lvl = img_lvl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

