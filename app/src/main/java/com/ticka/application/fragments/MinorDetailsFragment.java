package com.ticka.application.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.DropDownAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.custom.ValueChanger;
import com.ticka.application.helpers.BuildingHelper;
import com.ticka.application.models.HomeDataModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinorDetailsFragment extends Fragment implements BlockingStep {

    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private EditText buildingArea , landArea;
    private Spinner spLocation, spType;
    private ValueChanger roomsCounter;
    private int typeIdSelected = -1 , locationIdSelected = -1;
    private BuildingHelper buildingHelper = BuildingHelper.getInstance();

    public MinorDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_minor_details, container, false);

        initViews(view);
        init();

        return view;
    }

    private void initViews(View view){

        landArea     = view.findViewById(R.id.landArea);
        buildingArea = view.findViewById(R.id.buildingArea);
        roomsCounter = view.findViewById(R.id.roomsCounter);
        spLocation   = view.findViewById(R.id.spLeft);
        spType       = view.findViewById(R.id.spRight);

        spLocation.setAdapter(new DropDownAdapter(getContext() , getList(buildingHelper.getBuildingLocation())));
        spType.setAdapter(new DropDownAdapter(getContext() , getList(buildingHelper.getBuildingType())));

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationIdSelected = buildingHelper.getBuildingLocationId()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                locationIdSelected = 0;
            }
        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeIdSelected = buildingHelper.getBuildingTypeId()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeIdSelected = 0;
            }
        });
    }

    private List<String> getList(String[] strings){
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(strings));
        return list;
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
        else if(spType.getSelectedItemPosition() == 0 || spLocation.getSelectedItemPosition() == 0){
            return new VerificationError("اطلاعات ساختمان را صحیح و دقیق انتخاب کنید");
        }
        else {
            homesModel.setBuildingArea(Integer.valueOf(ba));
            homesModel.setLandArea(Integer.valueOf(la));
            homesModel.setBuildingType(typeIdSelected);
            homesModel.setLocationType(locationIdSelected);
            homesModel.setRoomNumber(roomsCounter.getValue());
            Logger.Log(homesModel.parsData());
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
