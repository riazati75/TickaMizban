package com.ticka.application;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.adapters.StepperAdapter;
import com.ticka.application.core.CentralCore;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.cities.CitiesModel;
import com.ticka.application.models.states.StatesModel;
import com.ticka.application.utils.JSONUtils;

public class NewActivity extends OptionActivity {

    private LinearLayout root;
    private StepperLayout stepperLayout;
    private DatabaseHelper databaseHelper;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void back(View view) {
        onBackPressed();
    }

    private boolean isExit = false;
    @Override
    public void onBackPressed() {

        if(!isExit){
            isExit = true;
            Toast.makeText(this, "برای بازگشت مجدد ضربه بزنید", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            } , 2000);
        }
        else{
            super.onBackPressed();
            overridePendingTransition(R.anim.animation_activity_show , R.anim.animation_activity_hide);
        }
    }
}
