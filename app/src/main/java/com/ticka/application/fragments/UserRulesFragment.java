package com.ticka.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.CheckboxAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomesModel;

public class UserRulesFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomesModel homesModel = HomesModel.getInstance();
    private RecyclerView recyclerView;
    private CheckboxAdapter adapter;

    public UserRulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_rules, container, false);

        context = container.getContext();

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL , false
        ));
        recyclerView.setHasFixedSize(true);

        adapter = new CheckboxAdapter(context , null);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        Logger.Log(homesModel.toString());
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
