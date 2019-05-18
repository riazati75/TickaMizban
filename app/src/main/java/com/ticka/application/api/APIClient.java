package com.ticka.application.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BODY_TEXT_TYPE = "text/plain";

    private static final String BASE_URL_ORGINAL = "https://api.ticka.com";
    private static final String BASE_URL_HOST    = "http://193.176.242.60:5030/ticka/home/public/api/v1/";

    static final String URL_LOGIN             = "SendLoginCode";
    static final String URL_VERIFICATION_CODE = "Token";
    static final String URL_INSERT_HOME       = "insert";

    private static Retrofit retrofit = null;

    private static Retrofit getInstance(){

        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    public static APIInterface getClient(){
        return APIClient.getInstance().create(APIInterface.class);
    }

}
