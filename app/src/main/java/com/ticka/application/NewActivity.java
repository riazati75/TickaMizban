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
import com.ticka.application.core.OptionActivity;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.facility.FacilityModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        getFacility();

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

    private void getFacility(){

        APIInterface api = APIClient.getTESTClient();
        api.getFacility().enqueue(new Callback<FacilityModel>() {
            @Override
            public void onResponse(Call<FacilityModel> call, Response<FacilityModel> response) {

                if(response.isSuccessful()){
                    databaseHelper.insertFacility(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<FacilityModel> call, Throwable t) {

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
    }
}
