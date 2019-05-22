package com.ticka.application.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.CheckboxAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomeDataModel;

public class FacilitiesFragment extends Fragment implements BlockingStep {

    private View root;
    private RecyclerView recyclerView;
    private EditText inputDescription;
    private CheckboxAdapter adapter;
    private HomeDataModel homesModel = HomeDataModel.getInstance();

    public FacilitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_fasilities, container, false);
        initView();
        initRecyclerView();
        return root;
    }

    private void initView(){
        recyclerView = root.findViewById(R.id.recycler);
        inputDescription = root.findViewById(R.id.inputDescription);
    }

    private void initRecyclerView(){

        recyclerView.setLayoutManager(new GridLayoutManager(
                getContext(), 2
        ));
        recyclerView.setHasFixedSize(true);

        adapter = new CheckboxAdapter(getContext() , CheckboxAdapter.FACILITY_TYPE);
        recyclerView.setAdapter(adapter);

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

    @Nullable
    @Override
    public VerificationError verifyStep() {

        if(adapter.getSelectedList().size() <= 3){
            return new VerificationError("حداقل 4 مورد را انتخاب کنید ");
        }
        else {
            homesModel.setFacilitiesArray(adapter.getSelectedList());
            homesModel.setFacilitiesDescription(inputDescription.getText().toString());
            Logger.Log(homesModel.parsData());
            return null;
        }
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
