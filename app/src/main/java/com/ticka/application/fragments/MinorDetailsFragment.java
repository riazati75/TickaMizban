package com.ticka.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Arrays;
import java.util.List;

public class MinorDetailsFragment extends Fragment implements BlockingStep {

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private EditText buildingArea , landArea;
    private Spinner spLocation, spType;
    private ValueChanger roomsCounter;
    private TextView ca1 , ca2;
    private int typeIdSelected = -1 , locationIdSelected = -1;

    private String[] type = {
            "نوع ساختمان",
            "ویلایی",
            "آپارتمان",
            "سویت",
            "خانه",
            "کلبه"
    };

    private int[] typeId = {
            0,
            1,
            2,
            3,
            4,
            5

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

    private int[] locationId = {
            01,
            12,
            23,
            34,
            45,
            56,
            67
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
        spLocation   = view.findViewById(R.id.spLeft);
        spType       = view.findViewById(R.id.spRight);
        ca1          = view.findViewById(R.id.ca1);
        ca2          = view.findViewById(R.id.ca2);

        spLocation.setAdapter(SpinnerHelper.getSpinnerAdapter(context , getList(location)));
        spType.setAdapter(SpinnerHelper.getSpinnerAdapter(context , getList(type)));

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationIdSelected = locationId[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                locationIdSelected = 0;
            }
        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeIdSelected = typeId[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeIdSelected = 0;
            }
        });

        ca1.setText(R.string.minor_1);
        ca2.setText(R.string.minor_2);

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
        else if(spType.getSelectedItemPosition() == 0 || spLocation.getSelectedItemPosition() == 0){
            return new VerificationError("اطلاعات ساختمان را صحیح و دقیق انتخاب کنید");
        }
        else {
            homesModel.setBuildingArea(Integer.valueOf(ba));
            homesModel.setLandArea(Integer.valueOf(la));
            homesModel.setBuildingType(String.valueOf(typeIdSelected));
            homesModel.setLocationType(String.valueOf(locationIdSelected));
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
