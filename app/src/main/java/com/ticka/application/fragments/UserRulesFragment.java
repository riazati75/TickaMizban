package com.ticka.application.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class UserRulesFragment extends Fragment implements BlockingStep {

    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private CheckboxAdapter adapter;
    private EditText inputDescription;

    public UserRulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_rules, container, false);

        inputDescription = root.findViewById(R.id.inputDescription);

        RecyclerView recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new CheckboxAdapter(getContext(), CheckboxAdapter.RULES_TYPE);
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
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {
        if(adapter.getSelectedList().size() < 1 && inputDescription.getText().toString().equals("")){
            return new VerificationError("حداقل 1 مورد را انتخاب کنید یا وارد کنید ");
        }
        else {
            homesModel.setRulesArray(adapter.getSelectedList());
            homesModel.setRuleDescription(inputDescription.getText().toString());
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
