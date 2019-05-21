package com.ticka.application.api;

import com.ticka.application.models.callback.LoginCallback;
import com.ticka.application.models.callback.SaveCallback;
import com.ticka.application.models.facility.FacilityModel;
import com.ticka.application.models.home.HomeModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @POST(APIClient.URL_LOGIN)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<Void> login(@Body RequestBody body);

    @POST(APIClient.URL_VERIFICATION_CODE)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<LoginCallback> verificationCode(@Body RequestBody body);

    @POST(APIClient.URL_INSERT_HOME)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<String> insertDate(@Body RequestBody body);

    @GET(APIClient.URL_GET_HOME)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<HomeModel> getHomes();

    @GET(APIClient.URL_GET_FACILITY)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<FacilityModel> getFacility();

    @GET(APIClient.URL_GET_PHOTO)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<SaveCallback> getPhoto(@Path("id") int id);

}