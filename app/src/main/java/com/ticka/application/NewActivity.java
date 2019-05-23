package com.ticka.application;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.adapters.StepperAdapter;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.CentralCore;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.cities.CitiesModel;
import com.ticka.application.models.facility.FacilityModel;
import com.ticka.application.models.rules.RuleData;
import com.ticka.application.models.states.StatesModel;
import com.ticka.application.utils.JSONUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewActivity extends OptionActivity {

    private LinearLayout root;
    private StepperLayout stepperLayout;
    private DatabaseHelper databaseHelper;
    private int requestFacility = 5;
    private int requestRules = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        init();
    }

    @Override
    protected void init() {

        databaseHelper = DatabaseHelper.getInstance(this);
        setOfflineCity();
        getFacility();
        getRules();

        root = findViewById(R.id.root);
        stepperLayout = findViewById(R.id.stepperLayout);

        initViews();
    }

    @Override
    protected void initViews() {

        StepperAdapter stepperAdapter = new StepperAdapter(getSupportFragmentManager(), this);
        stepperLayout.setAdapter(stepperAdapter, 0);
        stepperLayout.setListener(new StepperLayout.StepperListener() {
            @Override
            public void onCompleted(View completeButton) {

            }

            @Override
            public void onError(VerificationError verificationError) {
                Snackbar.make(root,
                        "خطا های موجود را تصحیح کنید و سپس ادامه دهید",
                        Snackbar.LENGTH_INDEFINITE
                ).setAction("باشه", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setActionTextColor(Color.YELLOW).show();
            }

            @Override
            public void onStepSelected(int newStepPosition) {

            }

            @Override
            public void onReturn() {
                finish();
            }
        });
    }

    private void setOfflineCity(){
        String states = JSONUtils.openJsonFromAssets(this , "json/states.json");
        String cities = JSONUtils.openJsonFromAssets(this , "json/cities.json");
        databaseHelper.insertStates(CentralCore.getGson().fromJson(states , StatesModel.class).getStates());
        databaseHelper.insertCities(CentralCore.getGson().fromJson(cities , CitiesModel.class).getCities());
    }

    private void getFacility(){

        APIInterface api = APIClient.getTESTClient();
        api.getFacility().enqueue(new Callback<FacilityModel>() {
            @Override
            public void onResponse(Call<FacilityModel> call, Response<FacilityModel> response) {

                if(response.isSuccessful()){

                    Logger.Log("Facility Success");
                    databaseHelper.insertFacility(response.body().getData());
                }
                else {

                    if(requestFacility > 0){
                        requestFacility = requestFacility - 1;
                        getFacility();
                    }
                }
            }

            @Override
            public void onFailure(Call<FacilityModel> call, Throwable t) {
                Logger.Log(t.getMessage());
                if(requestFacility > 0){
                    requestFacility = requestFacility - 1;
                    getFacility();
                }
            }
        });
    }

    private void getRules(){

        APIInterface api = APIClient.getTESTClient();
        api.getRule().enqueue(new Callback<List<RuleData>>() {
            @Override
            public void onResponse(Call<List<RuleData>> call, Response<List<RuleData>> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){

                        Logger.Log("Rule Success");
                        databaseHelper.insertRule(response.body());
                    }
                }
                else {

                    if(requestRules > 0){
                        requestRules = requestRules - 1;
                        getRules();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RuleData>> call, Throwable t) {

                Logger.Log(t.getMessage());

                if(requestRules > 0){
                    requestRules = requestRules - 1;
                    getRules();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation_activity_show , R.anim.animation_activity_hide);
    }
}
