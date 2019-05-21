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
import com.ticka.application.adapters.ReviewAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.helpers.BuildingHelper;
import com.ticka.application.models.HomeDataModel;

import java.util.List;

public class UserRulesFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private BuildingHelper buildingHelper = BuildingHelper.getInstance();
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView , rv;
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
        databaseHelper = DatabaseHelper.getInstance(context);
        setDialog();

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        adapter = new CheckboxAdapter(context , CheckboxAdapter.RULES_TYPE);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        if(!isReviewed){

            String facilities = "";
            String rules = "";

            List<String> facilitiesList = databaseHelper.getFacilitiesById(homesModel.getFacilitiesArray());
            for(int i = 0; i < facilitiesList.size(); i++){
                facilities = facilities + facilitiesList.get(i) + " , ";
            }

            String[] values = {
                    homesModel.getHomeTitle(),
                    databaseHelper.getStateNameById(homesModel.getHomeStateId()),
                    databaseHelper.getCityNameById(homesModel.getHomeCityId()),
                    homesModel.getHomeAddress(),
                    homesModel.getHomeDescription(),
                    buildingHelper.getBuildingTypeById(homesModel.getBuildingType()),
                    buildingHelper.getBuildingLocationById(homesModel.getLocationType()),
                    homesModel.getRoomNumber() + " اتاق",
                    homesModel.getLandArea() + " متر مربع",
                    homesModel.getBuildingArea() + " متر مربع",
                    homesModel.getStandardCapacity() + " نفر",
                    homesModel.getMaximumCapacity() + " نفر",
                    homesModel.getSingleBed() + " تخت",
                    homesModel.getDoubleBed() + " تخت",
                    homesModel.getExtraBed() + " دست",
                    homesModel.getCapacityDescription(),
                    facilities,
                    homesModel.getFacilitiesDescription(),
                    rules,
                    homesModel.getRuleDescription()
            };
            showDialog(values);
        }else{
            Logger.Log(homesModel.parsData());
            callback.complete();
        }
    }

    private void setDialog() {

        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_review_homes, false)
                .autoDismiss(false).cancelable(false)
                .positiveText("تایید").negativeText("لغو")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        isReviewed = true;
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        isReviewed = false;
                        dialog.dismiss();
                    }
                }).build();

        rv = (RecyclerView) dialog.findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(
                new LinearLayoutManager(
                        context, LinearLayoutManager.VERTICAL, false
                )
        );

        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_background));
        }
    }

    private void showDialog(String[] values){
        ReviewAdapter ra = new ReviewAdapter(context , values);
        rv.setAdapter(ra);
        dialog.show();
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
