package com.ticka.application.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.AddPhotoAdapter;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.models.HomeDataModel;
import com.ticka.application.models.UploadModel;
import com.ticka.application.models.callback.SaveCallback;
import com.ticka.application.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ir.farsroidx.StringImageUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhotoFragment extends Fragment implements BlockingStep {

    private static final int PICK_PHOTO_REQUEST = 100;

    private Context context;
    private HomeDataModel homesModel = HomeDataModel.getInstance();
    private AddPhotoAdapter adapter;
    private ImageView imageView;

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

    private void uploadFile(String base64) {

        APIInterface api = APIClient.getCDNClient();

        JSONObject object = JSONUtils.getSaveJson(base64);
        JsonObject jsonObject = JSONUtils.getSaveJson2(base64);

        //////////////////////////////////////////////////////////////////////////////

        UploadModel upload = new UploadModel("file.jpeg" , 1 , 1 , false , base64);

        //////////////////////////////////////////////////////////////////////////////

        RequestBody body = RequestBody.create(
                MediaType.parse(APIClient.BODY_TEXT_PLAIN_TYPE),
                object.toString());

        api.savePhoto(jsonObject).enqueue(new Callback<SaveCallback>() {
            @Override
            public void onResponse(Call<SaveCallback> call, Response<SaveCallback> response) {

                if(response.isSuccessful()){
                    if(response.body().isSuccessful()){
                        Logger.Log("fileId: " + response.body().getResult());
                        Toast.makeText(context, "ارسال موفق", Toast.LENGTH_SHORT).show();
                        adapter.setItemCount(adapter.getItemCount() + 1);
                    }else {
                        Logger.Log("error: " + response.body().getErrorMessage());
                        Toast.makeText(context, "خطا در ارسال تصویر. مجدد امتحان کنید", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Logger.Log("error: " + response.toString());
                    Toast.makeText(context, "خطایی رخ داده است", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveCallback> call, Throwable t) {
                Logger.Log("throwable: " + t.getMessage());
                Toast.makeText(context, "خطا در اتصال به شبکه", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadFileVolley(String base64) {

        String URL = "http://cdn.ticka.com/upload";
        final JSONObject jsonBody = JSONUtils.getSaveJson(base64);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int result = 0;

                try{
                    result = response.getInt("result" );
                }catch(JSONException e){
                    e.printStackTrace();
                }

                Logger.Log("Result: " + result);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.Log(error.toString());
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
