package com.ticka.application.helpers;

import android.content.Context;

import com.ticka.application.adapters.StateCityAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpinnerHelper {

    public static StateCityAdapter getSpinnerAdapter(Context context , String[] lists){
        List<String> list = new ArrayList<>();
        Collections.addAll(list, lists);
        return new StateCityAdapter(context , list);
    }

}
