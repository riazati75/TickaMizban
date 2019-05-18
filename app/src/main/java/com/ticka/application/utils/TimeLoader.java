package com.ticka.application.utils;

import android.os.Handler;

public class TimeLoader implements Runnable {

    private static final int UPDATE_TIME_MILLISECOND = 1000;

    private static TimeLoader timeLoader = null;
    private static Handler handler = null;
    private OnTimeChanged onTimeChanged;
    private int second = 0;

    public static TimeLoader getTimeLoader(){
        if(timeLoader == null){
            timeLoader = new TimeLoader();
        }
        return timeLoader;
    }

    private static Handler getHandler(){
        if(handler == null){
            handler = new Handler();
        }
        return handler;
    }

    public void startTimer(OnTimeChanged onTimeChanged){
        this.onTimeChanged = onTimeChanged;
        getHandler().post(getTimeLoader());
    }

    public void stopTimer(){
        getHandler().removeCallbacks(getTimeLoader());
    }

    public void setTimeLoader(int second){
        this.second = second;
    }

    @Override
    public void run() {

        if(second <= 0){
            onTimeChanged.onFinish();
            return;
        }

        second = second - 1;

        if(onTimeChanged != null){
            onTimeChanged.onChange(second);
        }

        getHandler().postDelayed(this , UPDATE_TIME_MILLISECOND);
    }

    public interface OnTimeChanged {
        void onChange(int millisecond);
        void onFinish();
    }
}
