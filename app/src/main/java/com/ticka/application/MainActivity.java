package com.ticka.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.cities.CitiesModel;
import com.ticka.application.models.home.HomeData;
import com.ticka.application.models.home.HomeModel;
import com.ticka.application.models.states.StatesModel;
import com.ticka.application.utils.JSONUtils;
import com.ticka.application.utils.SPUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends OptionActivity {

    private FloatingActionButton fab;
    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void init() {

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.root) , true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        fab = findViewById(R.id.fab);

        initViews();
    }

    @Override
    protected void initViews() {

        spUtils = SPUtils.getInstance(this);
        if(!spUtils.readBoolean("import", false)){
            DatabaseHelper database = DatabaseHelper.getInstance(this);
            String states = JSONUtils.openJsonFromAssets(this , "json/states.json");
            String cities = JSONUtils.openJsonFromAssets(this , "json/cities.json");
            Gson gson = new Gson();
            database.insertStates(gson.fromJson(states , StatesModel.class).getStates());
            database.insertCities(gson.fromJson(cities , CitiesModel.class).getCities());
            spUtils.writeBoolean("import" , true);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                MainActivity.this , NewActivity.class
                        )
                );
            }
        });


        getHome();
    }

    private void getHome(){

        APIInterface api = APIClient.getTESTClient();
        api.getHomes().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                if(response.body() != null){
                         List<HomeData> model = response.body().getData();
                    Logger.Log("Response: " + response.body().getCurrentPage());
                }
                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
