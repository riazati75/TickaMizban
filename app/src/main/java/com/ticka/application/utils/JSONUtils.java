package com.ticka.application.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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

    public static JSONObject getSaveJson(@NonNull String base64){

        JSONObject json = getJsonObject();

        try{
            json.put("Name" , "photo.jpeg");
            json.put("Type" , 4);
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
