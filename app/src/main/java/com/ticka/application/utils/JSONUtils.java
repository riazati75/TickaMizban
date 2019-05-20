package com.ticka.application.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static JsonObject getSaveJson2(@NonNull String base64){

        JSONObject json = new JSONObject();
        JsonObject object = new JsonObject();

        try{

            json.put("Name" , "photo.jpeg");
            json.put("Type" , 1);
            json.put("Size" , 1);
            json.put("HasThumbnail" , true);
            json.put("Base64Content" , "data:image/jpeg;base64," + base64);

            JsonParser parser = new JsonParser();
            object = (JsonObject) parser.parse(json.toString());

        }catch(JSONException e){
            e.printStackTrace();
        }

        return object;
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
