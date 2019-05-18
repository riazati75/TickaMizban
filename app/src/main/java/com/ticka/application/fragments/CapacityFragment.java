package com.ticka.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomesModel;
import com.ticka.application.widgets.ValueChanger;

public class CapacityFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomesModel homesModel = HomesModel.getInstance();
    private TextView ca1 , ca2 , ca3 , ca4 , description;
    private ValueChanger c1 , c2 , c3 , c4;
    private EditText inputDescription;

    public CapacityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_capacity, container, false);
        context = container.getContext();

        initViews(view);
        init();

        return view;
    }

    private void initViews(View view){

        ca1 = view.findViewById(R.id.ca1);
        ca2 = view.findViewById(R.id.ca2);
        ca3 = view.findViewById(R.id.ca3);
        ca4 = view.findViewById(R.id.ca4);
        description = view.findViewById(R.id.description);
        inputDescription = view.findViewById(R.id.inputDescription);

        ca1.setText(R.string.capacity_1);
        ca2.setText(R.string.capacity_2);
        ca3.setText(R.string.capacity_3);
        ca4.setText(R.string.capacity_4);
        description.setText(R.string.general_description);

        c1 = view.findViewById(R.id.c1);
        c2 = view.findViewById(R.id.c2);
        c3 = view.findViewById(R.id.c3);
        c4 = view.findViewById(R.id.c4);

    }

    private void init(){


    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Logger.Log(homesModel.parsData());
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
