package com.ticka.application.core;

import android.app.Application;

import com.ticka.application.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CentralCore extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupFont("sans_mobile_regular.ttf");
    }

    public void setupFont(String fontName){

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(fontName)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
