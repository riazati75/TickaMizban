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
import android.widget.Spinner;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.core.Logger;
import com.ticka.application.helpers.SpinnerHelper;
import com.ticka.application.models.HomeDataModel;
import com.ticka.application.custom.ValueChanger;

import java.util.ArrayList;

public class MinorDetailsFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private EditText buildingArea , landArea;
    private Spinner spLeft , spRight , spTip;
    private ValueChanger roomsCounter;
    private TextView ca1 , ca2;

    private String[] type = {
            "نوع ساختمان",
            "ویلایی",
            "آپارتمان",
            "سویت",
            "خانه",
            "کلبه"
    };

    private String[] location = {
            "نوع منطقه ای",
            "ساحلی",
            "شهری",
            "جنگلی",
            "کوهستانی",
            "کویری",
            "روستایی"
    };

    private String[] buildingTip = {
            "تیپ سازه",
            "هم سطح",
            "دوبلکس",
            "تریبلکس"
    };

    public MinorDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minor_details, container, false);
        context = container.getContext();

        initViews(view);
        init();

        return view;
    }

    private void initViews(View view){

        landArea     = view.findViewById(R.id.landArea);
        buildingArea = view.findViewById(R.id.buildingArea);
        roomsCounter = view.findViewById(R.id.roomsCounter);
        spLeft       = view.findViewById(R.id.spLeft);
        spRight      = view.findViewById(R.id.spRight);
        spTip        = view.findViewById(R.id.spTip);
        ca1          = view.findViewById(R.id.ca1);
        ca2          = view.findViewById(R.id.ca2);

        spLeft.setAdapter(SpinnerHelper.getSpinnerAdapter(context , new ArrayList<String>()));
        spRight.setAdapter(SpinnerHelper.getSpinnerAdapter(context , new ArrayList<String>()));
        spTip.setAdapter(SpinnerHelper.getSpinnerAdapter(context , new ArrayList<String>()));

        ca1.setText(R.string.minor_1);
        ca2.setText(R.string.minor_2);

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

        String ba = getText(buildingArea);
        String la = getText(landArea);

        if(la.equals("")){
            landArea.requestFocus();
            return new VerificationError("فیلد ها را با دقت پر کنید");
        }
        else if(ba.equals("")){
            buildingArea.requestFocus();
            return new VerificationError("فیلد ها را با دقت پر کنید");
        }
        else if(spRight.getSelectedItemPosition() == 0 || spLeft.getSelectedItemPosition() == 0){
            return new VerificationError("اطلاعات ساختمان را صحیح و دقیق انتخاب کنید");
        }
        else {
            homesModel.setBuildingArea(Integer.valueOf(ba));
            homesModel.setLandArea(Integer.valueOf(la));
            homesModel.setBuildingType(type[spRight.getSelectedItemPosition()]);
            homesModel.setLocationType(location[spLeft.getSelectedItemPosition()]);
            homesModel.setBuildingTip(buildingTip[spTip.getSelectedItemPosition()]);
            homesModel.setRoomNumber(roomsCounter.getValue());
            return null;
        }
    }

    private String getText(EditText editText){
        return editText.getText().toString().trim();
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
