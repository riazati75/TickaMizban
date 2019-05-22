package com.ticka.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomeDataModel;
import com.ticka.application.custom.ValueChanger;

public class CapacityFragment extends Fragment implements BlockingStep {

    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private ValueChanger c1 , c2 , c3 , c4 , c5;

    public CapacityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capacity, container, false);
        initViews(view);
        init();
        return view;
    }

    private void initViews(View view){

        TextView ca1 = view.findViewById(R.id.ca1);
        TextView ca2 = view.findViewById(R.id.ca2);
        TextView ca3 = view.findViewById(R.id.ca3);
        TextView ca4 = view.findViewById(R.id.ca4);
        TextView ca5 = view.findViewById(R.id.ca5);
        TextView description = view.findViewById(R.id.description);

        ca1.setText(R.string.capacity_1);
        ca2.setText(R.string.capacity_2);
        ca3.setText(R.string.capacity_3);
        ca4.setText(R.string.capacity_4);
        ca5.setText(R.string.capacity_5);
        description.setText(R.string.general_description);

        c1 = view.findViewById(R.id.c1);
        c2 = view.findViewById(R.id.c2);
        c3 = view.findViewById(R.id.c3);
        c4 = view.findViewById(R.id.c4);
        c5 = view.findViewById(R.id.c5);

    }

    private void init(){


    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        homesModel.setStandardCapacity(c1.getValue());
        homesModel.setMaximumCapacity(c2.getValue());
        homesModel.setSingleBed(c3.getValue());
        homesModel.setDoubleBed(c4.getValue());
        homesModel.setExtraBed(c5.getValue());
        Logger.Log(homesModel.parsData());
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
