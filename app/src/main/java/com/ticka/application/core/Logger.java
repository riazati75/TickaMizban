package com.ticka.application.core;

import android.util.Log;

public class Logger {

    public static void Log(String msg){
        Log.d("CentralCore" , msg);
    }

    public static void Log(int msg){
        Log.d("CentralCore" , String.valueOf(msg));
    }

    public static void Log(float msg){
        Log.d("CentralCore" , String.valueOf(msg));
    }

    public static void Log(long msg){
        Log.d("CentralCore" , String.valueOf(msg));
    }

    public static void Log(double msg){
        Log.d("CentralCore" , String.valueOf(msg));
    }

    public static void Log(short msg){
        Log.d("CentralCore" , String.valueOf(msg));
    }
}
