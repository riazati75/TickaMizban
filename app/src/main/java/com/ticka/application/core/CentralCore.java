package com.ticka.application.core;

import android.app.Application;

import com.google.gson.Gson;
import com.ticka.application.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CentralCore extends Application {

    private static Gson gson = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFont();
    }

    public void setupFont(){

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("yekan_mobile_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Gson getGson() {
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
