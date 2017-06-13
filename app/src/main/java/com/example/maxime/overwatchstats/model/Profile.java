package com.example.maxime.overwatchstats.model;


public class Profile {
    private String username;
    private String fullUsername;
    private String level;
    private String rank;
    private int time_qp;
    private int time_ranked;
    private int time_total;

    private String qp_losses;
    private String qp_wins;
    private String qp_winrate;

    private String ranked_losses;
    private String ranked_wins;
    private String ranked_winrate;

    private String img_rank;
    private String img_stars = null;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
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

    public String getQp_losses() {
        return qp_losses;
    }

    public void setQp_losses(String qp_losses) {
        this.qp_losses = qp_losses;
    }

    public String getQp_wins() {
        return qp_wins;
    }

    public void setQp_wins(String qp_wins) {
        this.qp_wins = qp_wins;
    }

    public String getQp_winrate() {
        return qp_winrate;
    }

    public void setQp_winrate(String qp_winrate) {
        this.qp_winrate = qp_winrate;
    }

    public String getRanked_losses() {
        return ranked_losses;
    }

    public void setRanked_losses(String ranked_losses) {
        this.ranked_losses = ranked_losses;
    }

    public String getRanked_wins() {
        return ranked_wins;
    }

    public void setRanked_wins(String ranked_wins) {
        this.ranked_wins = ranked_wins;
    }

    public String getRanked_winrate() {
        return ranked_winrate;
    }

    public void setRanked_winrate(String ranked_winrate) {
        this.ranked_winrate = ranked_winrate;
    }
}

