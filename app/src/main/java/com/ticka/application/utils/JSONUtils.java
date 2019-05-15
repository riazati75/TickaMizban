package com.ticka.application.utils;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

    private static JSONObject jsonObject;

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

}
