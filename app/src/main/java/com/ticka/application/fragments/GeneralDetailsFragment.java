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
import com.ticka.application.helpers.SpinnerHelper;

public class GeneralDetailsFragment extends Fragment implements BlockingStep {

    private Context context;
    private View view;
    private TextView title , state , city , address , description;
    private Spinner stateList , cityList;
    private EditText inputTitle , inputAddress , inputDescription;
    private String spState , spCity;

    private String[] listState = {
            "انتخاب کنید",
            "تهران",
            "کرج"
    };

    private String[] listCity = {
            "انتخاب کنید",
            "اندیشه",
            "ملارد"
    };

    public GeneralDetailsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general_details, container, false);
        context = container.getContext();

        initViews(view);
        init();

        initSpinnerState(listState);
        initSpinnerCity(listCity);

        return view;
    }

    private void initViews(View view){
        title = view.findViewById(R.id.title);
        state = view.findViewById(R.id.state);
        city = view.findViewById(R.id.city);
        address = view.findViewById(R.id.address);
        description = view.findViewById(R.id.description);
        stateList = view.findViewById(R.id.stateList);
        cityList = view.findViewById(R.id.cityList);
        inputTitle = view.findViewById(R.id.inputTitle);
        inputAddress = view.findViewById(R.id.inputAddress);
        inputDescription = view.findViewById(R.id.inputDescription);
    }

    private void init(){
        title.setText(R.string.general_title);
        state.setText(R.string.general_state);
        city.setText(R.string.general_city);
        address.setText(R.string.general_address);
        description.setText(R.string.general_description);
    }

    private void setError(EditText editText){
        editText.requestFocus();
    }

    private String getText(EditText editText){
        return editText.getText().toString().trim();
    }

    private void initSpinnerState(String[] list){
        stateList.setAdapter(SpinnerHelper.getSpinnerAdapter(context , list));
        stateList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spState = listState[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spState = listState[0];
            }
        });
    }

    private void initSpinnerCity(String[] list){
        cityList.setAdapter(SpinnerHelper.getSpinnerAdapter(context , list));
        cityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spCity = listCity[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spCity = listCity[0];
            }
        });
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

        String title   = getText(inputTitle);
        String address = getText(inputAddress);
        String desc    = getText(inputDescription);

        if(title.equals("")){
            setError(inputTitle);
            return new VerificationError("فیلد ها را با دقت پر کنید");
        }
        else if(address.equals("")){
            setError(inputAddress);
            return new VerificationError("فیلد ها را با دقت پر کنید");
        }
        else if(spState.equals(listState[0]) || spCity.equals(listCity[0])){
            return new VerificationError("استان و شهر را صحیح و دقیق انتخاب کنید");
        }
        else {
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
