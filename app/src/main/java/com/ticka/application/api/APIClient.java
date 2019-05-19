package com.ticka.application.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BODY_TEXT_TYPE = "text/plain";
    public static final String BODY_JSON_TYPE = "application/json";

    //private static final String BASE_URL_API_LOCALHOST  = "http://user.prv:8085/api/v1/";
    private static final String BASE_URL_API  = "http://api.ticka.com/";
    private static final String BASE_URL_CDN  = "http://cdn.ticka.com/";
    private static final String BASE_URL_HOST = "http://193.176.242.60:5030/ticka/home/public/api/v1/";

    static final String URL_LOGIN             = "Account/SendLoginCode";
    static final String URL_VERIFICATION_CODE = "Account/Token";
    static final String URL_SAVE_PHOTO        = "Upload";
    static final String URL_GET_PHOTO         = "File/{id}";
    static final String URL_INSERT_HOME       = "insert";

    private static Retrofit API = null , CDN = null;

    private static Retrofit getAPI(){

        if(API == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            API = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return API;
    }

    private static Retrofit getCDN(){

        if(CDN == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            CDN = new Retrofit.Builder()
                    .baseUrl(BASE_URL_CDN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return CDN;
    }

    public static APIInterface getAPIClient(){
        return APIClient.getAPI().create(APIInterface.class);
    }

    public static APIInterface getCDNClient(){
        return APIClient.getCDN().create(APIInterface.class);
    }

}
