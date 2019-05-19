package com.ticka.application.helpers;

import android.content.Context;

import com.ticka.application.adapters.StateCityAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpinnerHelper {

    public static StateCityAdapter getSpinnerAdapter(Context context , List<String> lists){
        return new StateCityAdapter(context , lists);
    }

}
