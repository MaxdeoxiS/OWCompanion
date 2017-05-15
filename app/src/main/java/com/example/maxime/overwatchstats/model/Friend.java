package com.example.maxime.overwatchstats.model;


public class Friend {
    private long _id;
    private String username;
    private String battleTag;
    private String portrait;

    public Friend(String u, String b)
    {
        this.username = u;
        this.battleTag = b;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }


}
