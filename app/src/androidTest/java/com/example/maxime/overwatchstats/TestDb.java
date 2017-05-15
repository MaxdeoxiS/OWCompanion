package com.example.maxime.overwatchstats;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.maxime.overwatchstats.model.DatabaseHandler;
import com.example.maxime.overwatchstats.model.FriendDAO;

/**
 * Created by Maxime on 20/03/2017.
 */

public class TestDb extends AndroidTestCase {

    protected final static String NAME = "database.db";
    protected final static int VERSION = 11;

    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(NAME);
        SQLiteDatabase db = new DatabaseHandler(mContext, NAME, null, VERSION).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertFriend() {
        String username = "testUsername";
        String battleTag = "testBattletag";

        SQLiteDatabase db = new DatabaseHandler(mContext, NAME, null, VERSION).getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(FriendDAO.USERNAME, username);
        value.put(FriendDAO.BATTLETAG, battleTag);

        long locationRowId;
        locationRowId = db.insert(FriendDAO.TABLE_NAME, null, value);

        assertTrue(locationRowId != -1);
    }
}
