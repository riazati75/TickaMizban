package com.ticka.application.api;

import com.ticka.application.models.callback.LoginCallback;
import com.ticka.application.models.callback.SaveCallback;
import com.ticka.application.models.facility.FacilityModel;
import com.ticka.application.models.home.HomeModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @POST(APIClient.URL_LOGIN)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<Void> login(@Body RequestBody body);

    @POST(APIClient.URL_VERIFICATION_CODE)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<LoginCallback> verificationCode(@Body RequestBody body);

    @GET(APIClient.URL_GET_HOME)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<HomeModel> getHomes();

    @GET(APIClient.URL_GET_FACILITY)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<FacilityModel> getFacility();

    @POST(APIClient.URL_GET_FACILITY)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    @FormUrlEncoded
    Call<String> insert(
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("cellphone") String cellphone,
            @Field("address") String address,
            @Field("description") String description,
            @Field("home_status_id") Integer home_status_id,
            @Field("city_id") Integer city_id,
            @Field("home_type") Integer home_type,
            @Field("place_area") Integer place_area,
            @Field("building_size") Integer building_size,
            @Field("area_size") Integer area_size,
            @Field("room_count") Integer room_count,
            @Field("base_capacity") Integer base_capacity,
            @Field("max_capacity") Integer max_capacity,
            @Field("single_bed") Integer single_bed,
            @Field("double_bed") Integer double_bed,
            @Field("extra_bed") Integer extra_bed,
            @Field("facility_array") List<Integer> facilityArray,
            @Field("rules_array") List<Integer> rulesArray,
            @Field("rules") String rules,
            @Field("image_array") List<Long> imageArray
    );

    @POST(APIClient.URL_INSERT_HOME)
    @FormUrlEncoded
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<String> insert(
            @FieldMap Map<String , Object> map
    );

    @POST(APIClient.URL_INSERT_HOME)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<String> insert(
            @Body RequestBody body
    );

    @GET(APIClient.URL_GET_PHOTO)
    @Headers({"accept: application/json",
            "Content-Type: application/json"})
    Call<SaveCallback> getPhoto(@Path("id") int id);

}