package com.ticka.application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.helpers.UserHelper;
import com.ticka.application.models.facility.FacilityData;
import com.ticka.application.models.facility.FacilityModel;
import com.ticka.application.models.rules.RuleData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends OptionActivity {

    public static final String ACTION_FORCE_CLOSE = "forceClose";

    private ConstraintLayout root;
    private ImageView photo;
    private Button button;
    private UserHelper userHelper;
    private DatabaseHelper databaseHelper;
    private AlertDialog alertDialog;
    private MaterialDialog dialog;
    private TextView txtProgress;
    private ProgressBar progressBar;
    private int requestFacility = 5;
    private int requestRules = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupNotificationBar();

        init();
    }

    @Override
    protected void init() {
        userHelper = UserHelper.getInstance(this);
        databaseHelper = DatabaseHelper.getInstance(this);

        root   = findViewById(R.id.root);
        photo  = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        List<FacilityData> list1 = databaseHelper.getFacilities();
        List<RuleData>     list2 = databaseHelper.getRules();

        if(list1.size() <= 0 || list2.size() <= 0){
            initDialog();
            dialogConfirmData();
        }
        else {
            initViews();
        }
    }

    @Override
    protected void initViews() {

        photo.startAnimation(AnimationUtils.loadAnimation(this , R.anim.animation_ticka_splash));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!userHelper.isUserJoined()){
                    button.setVisibility(View.VISIBLE);
                    button.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this , R.anim.animation_ticka_splash));
                }
                else {
                    initMain();
                }
            }
        } , 1800);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLogin();
            }
        });
    }

    private void dialogConfirmData() {

        alertDialog = new AlertDialog.Builder(SplashActivity.this , R.style.AppThemeDialog)
                .setCancelable(false)
                .setTitle("بروزرسانی:")
                .setIcon(R.drawable.icon_cloud_download)
                .setMessage("کاربر عزیز برای صحیح کار کردن برنامه باید اطلاعات از سرور بروزرسانی گردد.\n\n- از اتصال اینترنت خود اطمینان حاصل کرده و سپس اقدام به دریافت اطلاعات کنید.\n")
                .setPositiveButton("بروزرسانی", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new CheckingConnection().execute();
                    }
                })
                .setNegativeButton("خروج", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create();

        alertDialog.show();
    }

    private void initDialog(){

        dialog = new MaterialDialog.Builder(this)
                .customView(R.layout.dialog_loading_content , false)
                .cancelable(false)
                .autoDismiss(false)
                .build();

        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
        txtProgress = (TextView) dialog.findViewById(R.id.txtProgress);

        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        }
    }

    private void setLoadingProgress(int progress){
        String prg = progress + " %";
        txtProgress.setText(prg);
        progressBar.setProgress(progress);
    }

    private void getFacility(){

        setLoadingProgress(0);
        dialog.show();

        if(progressBar.getProgress() + 17 < 100){
            setLoadingProgress(progressBar.getProgress() + 20);
        }

        APIInterface api = APIClient.getTESTClient();
        api.getFacility().enqueue(new Callback<FacilityModel>() {
            @Override
            public void onResponse(Call<FacilityModel> call, Response<FacilityModel> response) {

                if(response.isSuccessful()){

                    if(response.body().getData() != null){

                        if(progressBar.getProgress() + 26 < 100){
                            setLoadingProgress(progressBar.getProgress() + 20);
                        }

                        Logger.Log("Facility Success");
                        databaseHelper.insertFacility(response.body().getData());

                        getRules();
                    }
                }
                else {

                    if(requestFacility > 0){
                        requestFacility = requestFacility - 1;
                        getFacility();
                    }
                }
            }

            @Override
            public void onFailure(Call<FacilityModel> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "خطا در اتصال به شبکه", Toast.LENGTH_SHORT).show();
                alertDialog.show();
                Logger.Log(t.getMessage());
                if(requestFacility > 0){
                    requestFacility = requestFacility - 1;
                    getFacility();
                }
            }
        });
    }

    private void getRules(){

        dialog.show();

        if(progressBar.getProgress() + 32 < 100){
            setLoadingProgress(progressBar.getProgress() + 20);
        }

        APIInterface api = APIClient.getTESTClient();
        api.getRule().enqueue(new Callback<List<RuleData>>() {
            @Override
            public void onResponse(Call<List<RuleData>> call, Response<List<RuleData>> response) {

                if(response.isSuccessful()){

                    if(response.body() != null){

                        if(progressBar.getProgress() + 25 <= 100){
                            setLoadingProgress(progressBar.getProgress() + 20);
                        }

                        Logger.Log("Rule Success");
                        databaseHelper.insertRule(response.body());

                        dialog.dismiss();

                        initViews();
                    }
                }
                else {

                    if(requestRules > 0){
                        requestRules = requestRules - 1;
                        getRules();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RuleData>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "خطا در اتصال به شبکه", Toast.LENGTH_SHORT).show();
                alertDialog.show();

                Logger.Log(t.getMessage());

                if(requestRules > 0){
                    requestRules = requestRules - 1;
                    getRules();
                }
            }
        });
    }

    private void initLogin(){

        Pair<View, String> p1 = Pair.create((View)button, "card");
        Pair<View, String> p2 = Pair.create((View)root, "root");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                p1,
                p2
        );

        startActivity(
                new Intent(
                        SplashActivity.this , LoginActivity.class
                ), options.toBundle()
        );

    }

    private void initMain(){

        Pair<View, String> p1 = Pair.create((View)root, "root");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                p1
        );

        startActivity(
                new Intent(
                        SplashActivity.this , MainActivity.class
                ), options.toBundle()
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        } , 800);
    }

    @SuppressLint("StaticFieldLeak")
    private class CheckingConnection extends AsyncTask<String,String,Boolean> {

        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(SplashActivity.this , R.style.AppThemeDialog);
            nDialog.setTitle("در حال اتصال");
            nDialog.setMessage("لطفا منتظر بمانید...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("https://google.com");
                    HttpURLConnection httpURL = (HttpURLConnection) url.openConnection();
                    httpURL.setConnectTimeout(4000);
                    httpURL.connect();
                    if (httpURL.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean isConnected){

            if(isConnected){
                nDialog.dismiss();
                getFacility();
            }
            else{
                nDialog.dismiss();
                alertDialog.show();
            }
        }
    }

    public void setupNotificationBar() {

        Window w = getWindow();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS ,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
