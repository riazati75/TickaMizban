package com.ticka.application.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BODY_TEXT_TYPE = "text/plain";

    private static final String BASE_URL            = "https://ticka.com/api/";
    private static final String BASE_URL_LOGIN_TEST = "http://user.prv:8085/api/v1/Account/";

    static final String URL_LOGIN             = "SendLoginCode";
    static final String URL_VERIFICATION_CODE = "Token";

    private static Retrofit retrofit = null;

    private static Retrofit getInstance(){

        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LOGIN_TEST)
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
