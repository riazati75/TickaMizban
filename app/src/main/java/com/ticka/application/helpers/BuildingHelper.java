package com.ticka.application.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.ticka.application.models.cities.CitiesModel;
import com.ticka.application.models.cities.City;
import com.ticka.application.models.states.State;
import com.ticka.application.models.states.StatesModel;
import com.ticka.application.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class BuildingHelper {

    private static Gson gson;

    private static Gson getGson(){

        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }

    public static List<String> getState(Context context){

        List<String> data = new ArrayList<>();
        String states = JSONUtils.openJsonFromAssets(context , "json/states.json");
        Gson gson = new Gson();
        List<State> stateList = gson.fromJson(states , StatesModel.class).getStates();
        data.add("انتخاب کنید");

        for(int i = 0; i < stateList.size(); i++){
            data.add(stateList.get(i).getName());
        }
        return data;
    }

    public static int getStateId(Context context , int position){

        String states = JSONUtils.openJsonFromAssets(context , "json/states.json");
        List<State> stateList = getStatesList(states);

        return Integer.parseInt(stateList.get(position).getId());
    }

    private static List<State> getStatesList(String state){
        return getGson().fromJson(state , StatesModel.class).getStates();
    }

    public static List<String> getCity(Context context , int stateId){

        List<String> data = new ArrayList<>();
        String cities = JSONUtils.openJsonFromAssets(context , "json/cities.json");

        List<City> cityList = getCitiesList(cities);

        data.add("انتخاب کنید");

        if(stateId == 0){
            return data;
        }

        for(int i = 0; i < cityList.size(); i++){
            if(cityList.get(i).getStateId().equals(String.valueOf(stateId))){
                data.add(cityList.get(i).getName());
            }
        }
        return data;
    }

    private static List<City> getCitiesList(String cities){
        return getGson().fromJson(cities , CitiesModel.class).getCities();
    }

}
