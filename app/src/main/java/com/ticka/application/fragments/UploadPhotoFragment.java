package com.ticka.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.PhotoGalleryAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomesModel;

import java.io.File;

public class UploadPhotoFragment extends Fragment implements BlockingStep {

    private static final int PICK_PHOTO_REQUEST_1 = 1;
    private static final int PICK_PHOTO_REQUEST_2 = 2;
    private static final int PICK_PHOTO_REQUEST_3 = 3;

    private Context context;
    private HomesModel homesModel = HomesModel.getInstance();
    private RecyclerView recycler;
    private FloatingActionButton fab;

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

        recycler = view.findViewById(R.id.recycler);
        fab = view.findViewById(R.id.fab);

        final PhotoGalleryAdapter adapter = new PhotoGalleryAdapter(context);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(
                context , LinearLayoutManager.VERTICAL , false
        ));
        recycler.setAdapter(adapter);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.setItemCount(adapter.getItemCount() + 1);
//                adapter.notifyDataSetChanged();
//            }
//        });
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

//            if (path != null) {
//                imgP1 = path;
//                path1.setText(imgP1);
//            }
//            img1.setImageURI(selectedImageUri);
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
//        if(path1.getText().toString().equals("")){
//            return new VerificationError("حداقل یک عکس باید انتخاب کنید");
//        }
//        else{
//            homesModel.setPhotoLocation1(imgP1);
//            homesModel.setPhotoLocation1(imgP2);
//            homesModel.setPhotoLocation1(imgP3);
//            return null;
//        }

        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
