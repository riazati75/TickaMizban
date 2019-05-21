package com.ticka.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.HomeDataModel;
import com.ticka.application.models.facility.FacilityData;

import java.util.List;

public class FacilitiesFragment extends Fragment implements BlockingStep {

    private Context context;
    private View root;
    private RecyclerView recyclerView;
    private CheckboxAdapter adapter;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private DatabaseHelper databaseHelper;

    public FacilitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fasilities, container, false);
        context = container.getContext();
        databaseHelper = DatabaseHelper.getInstance(context);

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(
                context, 2
        ));
        recyclerView.setHasFixedSize(true);

        List<FacilityData> facilityData = databaseHelper.getFacilities();

        Logger.Log("facility: " + facilityData.get(12).getName());

        adapter = new CheckboxAdapter(context , CheckboxAdapter.FACILITY_TYPE);
        recyclerView.setAdapter(adapter);

        return root;
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
        Logger.Log(homesModel.parsData());
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
