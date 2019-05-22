package com.ticka.application.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.AddPhotoAdapter;
import com.ticka.application.adapters.ReviewAdapter;
import com.ticka.application.core.Logger;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.helpers.BuildingHelper;
import com.ticka.application.helpers.ConnectionHelper;
import com.ticka.application.models.HomeDataModel;
import com.ticka.application.utils.JSONUtils;
import com.ticka.application.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.farsroidx.StringImageUtils;

import static com.ticka.application.helpers.UserHelper.KEY_USER_PHONE;

public class UploadPhotoFragment extends Fragment implements BlockingStep {

    private static final int PICK_PHOTO_REQUEST = 100;

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private BuildingHelper buildingHelper = BuildingHelper.getInstance();
    private DatabaseHelper databaseHelper;
    private AddPhotoAdapter adapter;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private boolean isSuccess = false;
    private String errorMessage = null;
    private List<Long> photoArrayList = new ArrayList<>();
    private long result = 0;
    private MaterialDialog dialog;
    private ProgressDialog progressDialog;
    private boolean isReviewed = false;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        context = container.getContext();
        databaseHelper = DatabaseHelper.getInstance(context);
        initViews(view);
        setDialog();
        return view;
    }

    private void initViews(View view){

        RecyclerView recycler = view.findViewById(R.id.recycler);

        adapter = new AddPhotoAdapter(context);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(
                context , LinearLayoutManager.VERTICAL , false
        ));
        recycler.setAdapter(adapter);

        adapter.setOnItemClicked(new AddPhotoAdapter.OnItemClicked() {
            @Override
            public void onClicked(int position, AddPhotoAdapter.ViewHolder viewHolder) {
                imageView = viewHolder.photo;
                pickImage();
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "عکس را انتخاب کنید:") , PICK_PHOTO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_PHOTO_REQUEST
                && resultCode == Activity.RESULT_OK) {

            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
            }catch(IOException e){
                e.printStackTrace();
            }
            String base64 = StringImageUtils.encodeToString(bitmap);
            Bitmap b = StringImageUtils.decodeToBitmap(base64);
            imageView.setImageBitmap(b);
            uploadFileVolley(base64);
        }
    }

    private void uploadFileVolley(String base64) {

        String URL = "http://cdn.ticka.com/upload";
        final JSONObject jsonBody = JSONUtils.getSaveJson(base64);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    isSuccess = response.getBoolean("succeed");
                    errorMessage = response.getString("errorMessage");
                    result = response.getLong("result");
                }catch(JSONException e){
                    e.printStackTrace();
                }

                if(isSuccess){
                    Toast.makeText(context, "ارسال موفق آمیز بود", Toast.LENGTH_SHORT).show();
                    photoArrayList.add(result);
                    adapter.setItemCount(adapter.getItemCount() + 1);
                }else {
                    Logger.Log("error: " + errorMessage);
                    Toast.makeText(context, "خطا در ارسال تصویر. مجدد امتحان کنید", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.Log("throwable: " + error.toString());
                Toast.makeText(context, "خطا در اتصال به شبکه", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        if(!isReviewed){

            homesModel.setPhotoArray(photoArrayList.toString());

            String facilities = "";
            String rules = "";

            List<String> facilitiesList = databaseHelper.getFacilitiesById(homesModel.getFacilitiesArray());
            for(int i = 0; i < facilitiesList.size(); i++){
                facilities = facilities + facilitiesList.get(i) + "\n";
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
                    facilities,
                    homesModel.getFacilitiesDescription(),
                    rules,
                    homesModel.getRuleDescription()
            };
            showDialog(values);
            Logger.Log(homesModel.parsData());
        }
        else {
            sendToServerConnectionHelper();
        }
    }

    private void sendToServerConnectionHelper(){

        progressDialog.show();

        String cellphone = SPUtils.getInstance(context)
                .readString(
                        KEY_USER_PHONE,
                        ""
                );

        new ConnectionHelper("http://193.176.242.60:7060/ticka/home/public/api/v1/insert-home" , 5000)
                .addStringRequest("name"           , homesModel.getHomeTitle())
                .addStringRequest("address"        , homesModel.getHomeAddress())
                .addStringRequest("description"    , homesModel.getHomeDescription())
                .addStringRequest("home_status_id" , String.valueOf(homesModel.getHomeStateId()))
                .addStringRequest("city_id"        , String.valueOf(homesModel.getHomeCityId()))
                .addStringRequest("home_type"      , String.valueOf(homesModel.getBuildingType()))
                .addStringRequest("place_area"     , String.valueOf(homesModel.getLocationType()))
                .addStringRequest("room_count"     , String.valueOf(homesModel.getRoomNumber()))
                .addStringRequest("base_capacity"  , String.valueOf(homesModel.getStandardCapacity()))
                .addStringRequest("max_capacity"   , String.valueOf(homesModel.getMaximumCapacity()))
                .addStringRequest("single_bed"     , String.valueOf(homesModel.getSingleBed()))
                .addStringRequest("double_bed"     , String.valueOf(homesModel.getDoubleBed()))
                .addStringRequest("extra_bed"      , String.valueOf(homesModel.getExtraBed()))
                //.addStringRequest("facility_id"    , homesModel.getFacilitiesArray())
                .addStringRequest("phone"          , homesModel.getPhone())
                .addStringRequest("cellphone"      , cellphone)
                //.addStringRequest("src"            , homesModel.getPhotoArray())
                .getStringResponse(new ConnectionHelper.OnStringResponse() {
                    @Override
                    public void notConnectToServer() {
                        progressDialog.dismiss();
                        Logger.Log("not connection");
                    }

                    @Override
                    public void onSuccessResponse(String result) {
                        progressDialog.dismiss();
                        Logger.Log("Response: " + result);
                    }

                    @Override
                    public void onNullResponse() {
                        progressDialog.dismiss();
                        Logger.Log("Null");
                    }
                });
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
                        sendToServerConnectionHelper();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        isReviewed = false;
                        dialog.dismiss();
                    }
                }).build();

        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        context, LinearLayoutManager.VERTICAL, false
                )
        );

        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_background));
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("در حال ثبت اقامتگاه");
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setIcon(R.drawable.icon_cloud_upload);
    }

    private void showDialog(String[] values){
        ReviewAdapter ra = new ReviewAdapter(context , values);
        recyclerView.setAdapter(ra);
        dialog.show();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public VerificationError verifyStep() {

        if(photoArrayList.size() == 0 || photoArrayList == null){
            return new VerificationError("حداقل یک عکس");
        }
        else {
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
