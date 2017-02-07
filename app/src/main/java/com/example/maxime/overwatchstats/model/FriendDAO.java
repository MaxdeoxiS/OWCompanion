package com.example.maxime.overwatchstats.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class FriendDAO extends DAOBase{

    public static final String TABLE_NAME = "friends";

    public static final String KEY = "id";

    public static final String USERNAME = "username";

    public static final String BATTLETAG = "battletag";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + BATTLETAG + " TEXT);";
    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public FriendDAO(Context context) {
        super(context);
    }

    public void add(Friend f) {

        ContentValues value = new ContentValues();

        value.put(FriendDAO.USERNAME, f.getUsername());
        value.put(FriendDAO.BATTLETAG, f.getBattleTag());

        mDb.insert(FriendDAO.TABLE_NAME, null, value);
    }

    public void delete(long id) {


    }

    public void update(Friend f) {


    }

    public Friend select(long id) {
        Cursor c = mDb.rawQuery("select " + USERNAME + " from " + TABLE_NAME + " where " + KEY + " = ?", new String[]{""+id});
        Friend f = null;

        if (c.moveToFirst()) {
            do {
                f = new Friend(id, c.getString(0), "test");
            } while (c.moveToNext());
        }
        return  f;
    }

    public int countFriends() {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[]{});

        return c.getColumnCount();
    }

    public void emptyTable() {
        mDb.delete(TABLE_NAME, null, null);
    }

}