package com.ticka.application.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLiteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
