package com.ticka.application.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.ticka.application.models.cities.City;
import com.ticka.application.models.states.State;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ticka.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_FACILITY = "facility";
    private static final String TABLE_CITY     = "city";
    private static final String TABLE_STATE    = "state";

    // Facility
    private static final String FACILITY_ID = "_id";

    // City
    private static final String CITY_PRIMARY  = "_id";
    private static final String CITY_ID       = "id";
    private static final String CITY_STATE_ID = "state_id";
    private static final String CITY_NAME     = "name";

    // State
    private static final String STATE_PRIMARY = "_id";
    private static final String STATE_ID      = "id";
    private static final String STATE_NAME    = "name";

    private static DatabaseHelper databaseHelper = null;
    private ContentValues contentValues = null;

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    private ContentValues getContentValues(){

        if(contentValues == null){
            contentValues = new ContentValues();
        }
        return contentValues;
    }

    public void insertStates(List<State> stateList){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues();

        for(int i = 0; i < stateList.size(); i++){
            contentValues.clear();
            contentValues.put(STATE_ID, stateList.get(i).getId());
            contentValues.put(STATE_NAME, stateList.get(i).getName());
            database.insert(TABLE_STATE , null , contentValues);
        }

        database.close();
    }

    public void insertCities(List<City> cityList){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = getContentValues();

        for(int i = 0; i < cityList.size(); i++){
            contentValues.clear();
            contentValues.put(CITY_NAME, cityList.get(i).getName());
            contentValues.put(CITY_ID, cityList.get(i).getId());
            contentValues.put(CITY_STATE_ID, cityList.get(i).getStateId());
            database.insert(TABLE_CITY , null , contentValues);
        }

        database.close();
    }

    @SuppressLint("Recycle")
    public List<State> getStates(){

        List<State> stateList = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_STATE , null);

        if(cursor.moveToFirst()){
            do{

                String id   = cursor.getString(cursor.getColumnIndex(STATE_ID));
                String name = cursor.getString(cursor.getColumnIndex(STATE_NAME));

                stateList.add(new State(
                        id , name
                ));
            }
            while(cursor.moveToNext());
        }

        database.close();
        return stateList;
    }

    @SuppressLint("Recycle")
    public String getStatesById(Integer stateId){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + STATE_NAME + " FROM " + TABLE_STATE + " WHERE " + STATE_ID + " = " + stateId , null);
        cursor.moveToFirst();
        database.close();
        return cursor.getString(cursor.getColumnIndex(STATE_NAME));
    }

    @SuppressLint("Recycle")
    public List<City> getCities(String stateId){

        List<City> stateList = new ArrayList<>();

        if(stateId.equals("0")){
            return stateList;
        }

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_CITY + " WHERE " + CITY_STATE_ID + " = '" + stateId + "'", null);

        if(cursor.moveToFirst()){
            do{

                String id = cursor.getString(cursor.getColumnIndex(CITY_ID));
                String state_id = cursor.getString(cursor.getColumnIndex(CITY_STATE_ID));
                String name     = cursor.getString(cursor.getColumnIndex(CITY_NAME));

                stateList.add(new City(
                        id , name , state_id
                ));
            }
            while(cursor.moveToNext());
        }

        database.close();
        return stateList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_STATE + " (" + STATE_PRIMARY + " Integer PRIMARY KEY AUTOINCREMENT , " +
                STATE_ID + " TEXT ," +
                STATE_NAME + " TEXT " +
                ")");

        db.execSQL("CREATE TABLE " + TABLE_CITY + " (" + CITY_PRIMARY + " Integer PRIMARY KEY AUTOINCREMENT , " +
                CITY_STATE_ID + " TEXT ," +
                CITY_ID       + " TEXT ," +
                CITY_NAME     + " TEXT " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATE);
            onCreate(db);
        }
    }
}
