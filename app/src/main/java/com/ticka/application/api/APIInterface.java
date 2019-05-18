package com.ticka.application.api;

import com.ticka.application.models.LoginCallback;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    @POST(APIClient.URL_LOGIN)
    @Headers({
            "accept: application/json",
            "Content-Type: application/json"
    })
    Call<Void> login(@Body RequestBody body);

    @POST(APIClient.URL_VERIFICATION_CODE)
    @Headers({
            "accept: application/json",
            "Content-Type: application/json"
    })
    Call<LoginCallback> verificationCode(@Body RequestBody body);

    @POST(APIClient.URL_VERIFICATION_CODE)
    @Headers({
            "accept: application/json",
            "Content-Type: application/json"
    })
    Call<String> insertDate(@Body RequestBody body);

}