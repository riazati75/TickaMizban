package com.ticka.application.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ticka.application.models.HomeDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONUtils {

    public static JSONObject getJsonPhone(String phone){

        JSONObject json = new JSONObject();

        try{
            json.put("userName" , phone);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject getJsonCode(@NonNull String userName , @NonNull String verificationCode){

        JSONObject json = new JSONObject();

        try{
            json.put("userName" , userName);
            json.put("password" , "");
            json.put("verificationCode" , verificationCode);
            json.put("grantType" , "VerificationCode");
            json.put("isPersistence" , true);
            json.put("clientIp" , "");
            json.put("clientId" , 0);
            json.put("refreshToken" , "RefreshToken");
        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject getJsonInsert(@NonNull HomeDataModel homesModel , Integer userId , String cellphone){

        JSONObject json = new JSONObject();
        JSONArray facilityArray = new JSONArray(homesModel.getFacilitiesArray());
        JSONArray rulesArray = new JSONArray(homesModel.getRulesArray());
        JSONArray photoArray = new JSONArray(homesModel.getPhotoArray());

        try{

            json.put("userId"               , userId);
            json.put("name"                 , homesModel.getHomeTitle());
            json.put("cellphone"            , cellphone);
            json.put("phone"                , homesModel.getPhone());
            json.put("address"              , homesModel.getHomeAddress());
            json.put("description"          , homesModel.getHomeDescription());
            json.put("home_status_id"       , homesModel.getHomeStateId());
            json.put("city_id"              , homesModel.getHomeCityId());
            json.put("home_type"            , homesModel.getBuildingType());
            json.put("place_area"           , homesModel.getLocationType());
            json.put("building_size"        , homesModel.getBuildingArea());
            json.put("area_size"            , homesModel.getLandArea());
            json.put("room_count"           , homesModel.getRoomNumber());
            json.put("base_capacity"        , homesModel.getStandardCapacity());
            json.put("max_capacity"         , homesModel.getMaximumCapacity());
            json.put("single_bed"           , homesModel.getSingleBed());
            json.put("double_bed"           , homesModel.getDoubleBed());
            json.put("extra_bed"            , homesModel.getExtraBed());
            json.put("facility_description" , homesModel.getFacilitiesDescription());
            json.put("rules"                , homesModel.getRuleDescription());

            if(homesModel.getFacilitiesArray().size() > 0){
                json.put("facility_array"   , facilityArray);
            }

            if(homesModel.getRulesArray().size() > 0){
                json.put("rules_array"      , rulesArray);
            }

            if(homesModel.getPhotoArray().size() > 0){
                json.put("image_array"      , photoArray);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject getSaveJson(@NonNull String base64){

        JSONObject json = new JSONObject();

        try{

            json.put("Name" , "photo.jpeg");
            json.put("Type" , 1);
            json.put("Size" , 1);
            json.put("HasThumbnail" , true);
            json.put("Base64Content" , "data:image/jpeg;base64," + base64);

        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

    public static String openJsonFromAssets(Context context , String fileName){

        try{
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer , StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
