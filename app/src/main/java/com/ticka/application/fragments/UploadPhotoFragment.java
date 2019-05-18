package com.ticka.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomesModel;

import java.io.File;

public class UploadPhotoFragment extends Fragment implements BlockingStep {

    private static final int PICK_PHOTO_REQUEST_1 = 1;
    private static final int PICK_PHOTO_REQUEST_2 = 2;
    private static final int PICK_PHOTO_REQUEST_3 = 3;

    private Context context;
    private HomesModel homesModel = HomesModel.getInstance();
    private ImageView img1 , img2 , img3;
    private TextView path1 , path2 , path3;
    private File file1 , file2 , file3;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        context = container.getContext();

        initViews(view);

        return view;
    }

    private void initViews(View view){

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);

        path1 = view.findViewById(R.id.path1);
        path2 = view.findViewById(R.id.path2);
        path3 = view.findViewById(R.id.path3);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(2);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(3);
            }
        });

    }

    public void pickImage(int position) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        switch(position){

            case 1:
                startActivityForResult(Intent.createChooser(intent, "عکس را انتخاب کنید:") , PICK_PHOTO_REQUEST_1);
                break;

            case 2:
                startActivityForResult(Intent.createChooser(intent, "عکس را انتخاب کنید:") , PICK_PHOTO_REQUEST_2);
                break;

            case 3:
                startActivityForResult(Intent.createChooser(intent, "عکس را انتخاب کنید:") , PICK_PHOTO_REQUEST_3);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_REQUEST_1
                && resultCode == Activity.RESULT_OK) {

            Uri selectedImageUri = data.getData();

            String path = null;
            if(selectedImageUri != null){
                path = selectedImageUri.getPath();
            }

            if (path != null) {
                path1.setText(path);
            }
            img1.setImageURI(selectedImageUri);
        }
        else if(requestCode == PICK_PHOTO_REQUEST_2
                && resultCode == Activity.RESULT_OK){

            Uri selectedImageUri = data.getData();

            String path = null;
            if(selectedImageUri != null){
                path = selectedImageUri.getPath();
            }

            if (path != null) {
                path2.setText(path);
            }
            img2.setImageURI(selectedImageUri);

        }
        else if(requestCode == PICK_PHOTO_REQUEST_3
                && resultCode == Activity.RESULT_OK){

            Uri selectedImageUri = data.getData();

            String path = null;
            if(selectedImageUri != null){
                path = selectedImageUri.getPath();
            }

            if (path != null) {
                path3.setText(path);
            }
            img3.setImageURI(selectedImageUri);
        }
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
        if(path1.getText().toString().equals("")){
            return new VerificationError("حداقل یک عکس باید انتخاب کنید");
        }
        else{
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
