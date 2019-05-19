package com.ticka.application.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BODY_TEXT_TYPE       = "";
    public static final String BODY_TEXT_PLAIN_TYPE = "text/plain";
    public static final String BODY_JSON_TYPE       = "application/json";
    public static final String BODY_JAVASCRIPT_TYPE = "application/javascript";
    public static final String BODY_XML_TYPE        = "application/xml";
    public static final String BODY_TEXT_XML_TYPE   = "text/xml";
    public static final String BODY_TEXT_HTML_TYPE  = "text/html";

    private static final String BASE_URL_API  = "http://api.ticka.com/";
    private static final String BASE_URL_CDN  = "http://cdn.ticka.com/";
    private static final String BASE_URL_TEST = "http://193.176.242.60:5030/ticka/home/public/api/v1/";

    static final String URL_LOGIN             = "Account/SendLoginCode";
    static final String URL_VERIFICATION_CODE = "Account/Token";
    static final String URL_SAVE_PHOTO        = "upload";
    static final String URL_GET_PHOTO         = "File/{id}";
    static final String URL_INSERT_HOME       = "insert-home";
    static final String URL_GET_HOME          = "home";

    public static final String CREATE_HOME_SUCCESS = "Home Inserted Successfully";

    private static Retrofit API = null , CDN = null , TEST = null;
    private static Gson GSON = null;

    private static Gson getGson(){

        if(GSON == null){
            GSON = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        return GSON;
    }

    private static Retrofit getAPI(){

        if(API == null){

            API = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }

        return API;
    }

    private static Retrofit getCDN(){

        if(CDN == null){

            CDN = new Retrofit.Builder()
                    .baseUrl(BASE_URL_CDN)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }

        return CDN;
    }

    private static Retrofit getTEST(){

        if(TEST == null){

            TEST = new Retrofit.Builder()
                    .baseUrl(BASE_URL_TEST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }

        return TEST;
    }

    public static APIInterface getAPIClient(){
        return APIClient.getAPI().create(APIInterface.class);
    }

    public static APIInterface getCDNClient(){
        return APIClient.getCDN().create(APIInterface.class);
    }

    public static APIInterface getTESTClient(){
        return APIClient.getTEST().create(APIInterface.class);
    }

}
