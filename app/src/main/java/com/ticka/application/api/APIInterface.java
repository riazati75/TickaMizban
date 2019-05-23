package com.ticka.application.api;

import com.ticka.application.models.callback.LoginCallback;
import com.ticka.application.models.facility.FacilityModel;
import com.ticka.application.models.home.HomeModel;
import com.ticka.application.models.rules.RuleData;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @POST(APIClient.URL_LOGIN)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<Void> login(@Body RequestBody body);

    @POST(APIClient.URL_VERIFICATION_CODE)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<LoginCallback> verificationCode(@Body RequestBody body);

    @GET(APIClient.URL_GET_HOME)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<HomeModel> getHomes(@Query("user_id") Integer id);

    @GET(APIClient.URL_GET_FACILITY)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<FacilityModel> getFacility();

    @GET(APIClient.URL_GET_RULE)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<List<RuleData>> getRule();

    @POST(APIClient.URL_INSERT_HOME)
    @Headers({"accept: application/json", "Content-Type: application/json"})
    Call<String> insert(@Body RequestBody body);

}