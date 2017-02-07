package com.example.maxime.overwatchstats.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String FRIENDS_KEY = "id";
    public static final String FRIENDS_INTITULE = "username";
    public static final String FRIENDS_SALAIRE = "battletag";



    public static final String FRIENDS_TABLE_NAME = "FRIENDS";
    public static final String FRIENDS_TABLE_CREATE =

            "CREATE TABLE " + FRIENDS_TABLE_NAME + " (" +

                    FRIENDS_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                    FRIENDS_INTITULE + " TEXT, " +

                    FRIENDS_SALAIRE + " REAL);";

    public static final String FRIENDS_TABLE_DROP = "DROP TABLE IF EXISTS " + FRIENDS_TABLE_NAME + ";";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(FRIENDS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(FRIENDS_TABLE_DROP);
        onCreate(db);

    }

}