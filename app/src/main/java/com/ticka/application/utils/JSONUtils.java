package com.ticka.application.utils;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

    private static JSONObject jsonObject = null;

    private static JSONObject getJsonObject(){
        if(jsonObject == null){
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public static JSONObject getJsonPhone(String phone){

        JSONObject json = getJsonObject();

        try{
            json.put("userName" , phone);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

    public static JSONObject getJsonCode(@NonNull String userName , @NonNull String verificationCode){

        JSONObject json = getJsonObject();

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

    public static JSONObject getSaveJson(@NonNull String name , Integer type , Integer size , String base64){

        JSONObject json = getJsonObject();

        try{
            json.put("Name" , name);
            json.put("Type" , type);
            json.put("Size" , size);
            json.put("HasThumbnail" , true);
            json.put("Base64Content" , base64);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return json;
    }

}
