package com.ticka.application.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.helpers.BuildingHelper;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ticka.application.helpers.UserHelper.KEY_USER_ID;
import static com.ticka.application.helpers.UserHelper.KEY_USER_PHONE;

public class UploadPhotoFragment extends Fragment implements BlockingStep {

    private static final int PICK_PHOTO_REQUEST = 1000;
    private static final int REQUEST_CODE = 1070;

    private AppCompatActivity context;
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
    private ProgressDialog alertDialog;
    private MaterialDialog dialog;
    private ProgressDialog progressDialog;
    private boolean isReviewed = false;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        context = (AppCompatActivity) getContext();
        databaseHelper = DatabaseHelper.getInstance(context);
        dialogWait();
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

        if(ContextCompat.checkSelfPermission(context , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(context,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "عکس را انتخاب کنید:") , PICK_PHOTO_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){

            Toast.makeText(context, "برای انتخاب عکس برنامه نیاز به مجوز خواندن حافظه دارد", Toast.LENGTH_SHORT).show();

        }
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

        alertDialog.show();

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
                    alertDialog.dismiss();
                    adapter.setItemCount(adapter.getItemCount() + 1);
                }else {
                    Logger.Log("error: " + errorMessage);
                    alertDialog.dismiss();
                    Toast.makeText(context, "خطا در ارسال تصویر. مجدد امتحان کنید", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.Log("throwable: " + error.toString());
                alertDialog.dismiss();
                Toast.makeText(context, "خطا در اتصال به شبکه مجدد امتحان کنید", Toast.LENGTH_SHORT).show();
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

            homesModel.setPhotoArray(photoArrayList);

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
            sendToServe();
        }
    }

    private void sendToServe(){

        progressDialog.show();

        final String cellphone = SPUtils.getInstance(context)
                .readString(
                        KEY_USER_PHONE,
                        ""
                );

        int userId = SPUtils.getInstance(context)
                .readInteger(
                        KEY_USER_ID,
                        0
                );

        APIInterface api = APIClient.getTESTClient();
        JSONObject json = JSONUtils.getJsonInsert(homesModel , userId , cellphone);

        RequestBody body = RequestBody.create(
                MediaType.parse(APIClient.BODY_TEXT_PLAIN_TYPE),
                json.toString()
        );

        api.insert(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()){

                    Logger.Log(response.body());

                    if(response.body().contains(APIClient.CREATE_HOME_SUCCESS)){
                        Intent intent = new Intent();
                        intent.putExtra("callback" , "ok");
                        context.setResult(Activity.RESULT_OK , intent);
                        context.finish();
                    }
                    else {
                        Logger.Log(response.body());
                        Toast.makeText(context, "ثبت اقامتگاه با خطا مواجه شد مجدد تست کنید", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Logger.Log(response.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Logger.Log("Throwable: => " + t.getMessage());
            }
        });
    }

    private void dialogWait() {

        alertDialog = new ProgressDialog(context , R.style.AppThemeDialog);
        alertDialog.setIndeterminate(false);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("در حال ارسال عکس");
        alertDialog.setIcon(R.drawable.icon_cloud_upload);
        alertDialog.setMessage("لطفا شکیبا باشید...");
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
                        sendToServe();
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
