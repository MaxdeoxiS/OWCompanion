package com.example.maxime.overwatchstats.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class FriendDAO extends DAOBase {

    public static final String TABLE_NAME = "friends";

    public static final String KEY = "id";

    public static final String USERNAME = "username";

    public static final String BATTLETAG = "battletag";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + BATTLETAG + " TEXT);";
    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public FriendDAO(Context context) {
        super(context);
    }

    public boolean add(Friend f) {

        if (null == this.search(f.getUsername())) {

            ContentValues value = new ContentValues();

            value.put(FriendDAO.USERNAME, f.getUsername());
            value.put(FriendDAO.BATTLETAG, f.getBattleTag());

            mDb.insert(FriendDAO.TABLE_NAME, null, value);
            return true;
        }
        return false;
    }

    public boolean register(Friend f) {

        if (null == this.search(f.getUsername())) {

            ContentValues value = new ContentValues();

            value.put(FriendDAO.KEY, 1);
            value.put(FriendDAO.USERNAME, f.getUsername());
            value.put(FriendDAO.BATTLETAG, f.getBattleTag());

            mDb.insert(FriendDAO.TABLE_NAME, null, value);
            return true;
        }
        return false;
    }

    public boolean delete(String battleTag) {
        if (null == this.search(battleTag)) {
            return false;
        }

        mDb.delete(TABLE_NAME, BATTLETAG + " = ? and " + KEY + " != ?" , new String[]{battleTag, "1"});
            return true;
    }

    public Friend search(String username) {
        Cursor c = mDb.rawQuery("select " + USERNAME + " from " + TABLE_NAME + " where " + USERNAME + " = ? and " +KEY + " != ?" , new String[]{username, "1"});
        Friend f = null;

        if (c.moveToFirst()) {
            do {
                f = new Friend(c.getString(0), c.getString(0));
            } while (c.moveToNext());
        }
        return  f;
    }

    public Friend select(long id) {
        Cursor c = mDb.rawQuery("select " + USERNAME + " , " + BATTLETAG + " from " + TABLE_NAME + " where " + KEY + " = ?", new String[]{""+id});
        Friend f = null;

        if (c.moveToFirst()) {
            do {
                f = new Friend(c.getString(0), c.getString(1));
            } while (c.moveToNext());
        }
        return  f;
    }

    public int countFriends() {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[]{});

        return c.getColumnCount();
    }

    public ArrayList<Friend> getAllFriends() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[]{});
        Friend f = null;

        int i = 1;

        if (c.moveToFirst()) {
            do {
                f = new Friend(c.getString(1), c.getString(2));
                if(i != 1)
                    friends.add(f);
                i++;
            } while (c.moveToNext());
        }

        return friends;
    }

    public void emptyTable() {
        mDb.delete(TABLE_NAME, null, null);
    }

}