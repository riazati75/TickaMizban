package com.ticka.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomeDataModel;

public class PriceFragment extends Fragment implements BlockingStep {

    private HomeDataModel homesModel = HomeDataModel.getInstance();

    public PriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_price, container, false);



        return root;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        Logger.Log(homesModel.parsData());
        callback.complete();
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
