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
import com.ticka.application.core.OptionActivity;

public class NewActivity extends OptionActivity {

    private LinearLayout root;
    private StepperLayout stepperLayout;
    private StepperAdapter stepperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        init();
    }

    @Override
    protected void init() {

        root = findViewById(R.id.root);
        stepperLayout = findViewById(R.id.stepperLayout);

        stepperAdapter = new StepperAdapter(getSupportFragmentManager(), this);
        stepperLayout.setAdapter(stepperAdapter);
        stepperLayout.setListener(new StepperLayout.StepperListener() {
            @Override
            public void onCompleted(View completeButton) {

            }

            @Override
            public void onError(VerificationError verificationError) {
                Snackbar.make(root,
                        "خطا های موجود را تصحیح کنید و سپس  ادامه دهید",
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

        initViews();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void back(View view) {
        onBackPressed();
    }
}
