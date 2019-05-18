package com.ticka.application.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SPUtils {

    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static SPUtils spUtils = null;
    private SharedPreferences sharedPreferences = null;

    public SPUtils(Context context) {
        this.context = context;
    }

    public static SPUtils getInstance(Context context){
        if(spUtils == null){
            spUtils = new SPUtils(context);
        }
        return spUtils;
    }

    private SharedPreferences getPreferenceInstance() {
        if(sharedPreferences == null){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sharedPreferences;
    }

    public String readString(String key, String alternative) {
        return getPreferenceInstance().getString(key, alternative);
    }

    public float readFloat(String key, float alternative) {
        return getPreferenceInstance().getFloat(key, alternative);
    }

    public long readLong(String key, long alternative) {
        return getPreferenceInstance().getLong(key, alternative);
    }

    public int readInteger(String key, int alternative) {
        return getPreferenceInstance().getInt(key, alternative);
    }

    public boolean readBoolean(String key, boolean alternative) {
        return getPreferenceInstance().getBoolean(key, alternative);
    }

    public void writeString(String key, String str) {
        SharedPreferences.Editor editor = getPreferenceInstance().edit();
        editor.putString(key, str);
        editor.apply();
    }

    public void writeFloat(String key, float flt) {
        SharedPreferences.Editor editor = getPreferenceInstance().edit();
        editor.putFloat(key , flt);
        editor.apply();
    }

    public void writeLong(String key, long lng) {
        SharedPreferences.Editor editor = getPreferenceInstance().edit();
        editor.putLong(key,lng);
        editor.apply();
    }

    public void writeInteger(String key, int integer) {
        SharedPreferences.Editor editor = getPreferenceInstance().edit();
        editor.putInt(key, integer);
        editor.apply();
    }

    public void writeBoolean(String key, boolean bln) {
        SharedPreferences.Editor editor = getPreferenceInstance().edit();
        editor.putBoolean(key, bln);
        editor.apply();
    }

}