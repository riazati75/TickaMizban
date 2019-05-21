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

import com.afollestad.materialdialogs.MaterialDialog;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.CheckboxAdapter;
import com.ticka.application.adapters.ReviewAdaptert;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomeDataModel;

public class UserRulesFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private RecyclerView recyclerView;
    private CheckboxAdapter adapter;
    private MaterialDialog dialog;
    private boolean isReviewed = false;

    public UserRulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user_rules, container, false);
        context = container.getContext();
        setDialog();

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
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        if(!isReviewed){
            dialog.show();
        }
        else {
            Logger.Log(homesModel.parsData());
            callback.complete();
        }
    }

    private void setDialog(){

        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_review_homes , false)
                .autoDismiss(false)
                .positiveText("تایید")
                .negativeText("لغو")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        isReviewed = true;
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        isReviewed = false;
                    }
                })
                .build();

        RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(
                context , LinearLayoutManager.VERTICAL , false
        ));
        ReviewAdaptert ra = new ReviewAdaptert(context);
        rv.setAdapter(ra);
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
