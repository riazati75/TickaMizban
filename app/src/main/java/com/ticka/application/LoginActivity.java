package com.ticka.application;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.utils.JSONUtils;

import org.json.JSONObject;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ticka.application.SplashActivity.ACTION_FORCE_CLOSE;

public class LoginActivity extends OptionActivity {

    private static final int REQUEST_CODE = 1070;

    private FrameLayout root;
    private MaskedEditText inPhone;
    private CardView cardView;
    private Button button;
    private String phone = "";
    private BroadcastReceiver receiver;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupNotificationBar();
        init();
    }

    @Override
    protected void init() {

        root    = findViewById(R.id.frameLayout);
        cardView = findViewById(R.id.card);
        inPhone = findViewById(R.id.inPhone);
        button  = findViewById(R.id.button);

        setProgressDialog();
        initViews();
    }

    @Override
    protected void initViews() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phone.trim().length() < 13 || phone.trim().length() > 13){
                    inPhone.setError("شماره صحیح نیست");
                    inPhone.requestFocus();
                }
                else{
                    dialogConfirmPhone();
                }
            }
        });

        inPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() <= 23){
                    phone =  getPhone(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void setProgressDialog(){

        progressDialog = new ProgressDialog(LoginActivity.this , R.style.AppThemeDialog);
        progressDialog.setTitle("در حال اتصال");
        progressDialog.setMessage("لطفا منتظر بمانید...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    private void dialogConfirmPhone() {

        AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this , R.style.AppThemeDialog)
                .setMessage("آیا از ارسال کد فعال سازی به " + phone + " اطمینان کامل دارید؟")
                .setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermission();
                    }
                })
                .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LoginActivity.this, "ارسال کد فعال سازی لغو شد", Toast.LENGTH_SHORT).show();
                    }
                }).create();

        dialog.show();
    }

    private void sendPhone(final String phone){

        JSONObject object = JSONUtils.getJsonPhone(phone);

        RequestBody body = RequestBody.create(
                MediaType.parse(APIClient.BODY_TEXT_PLAIN_TYPE),
                object.toString());

        progressDialog.show();

        APIInterface api = APIClient.getAPIClient();
        api.login(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Logger.Log("Response: " + response.message());

                if(response.isSuccessful()){
                    initCode(phone);
                    Toast.makeText(LoginActivity.this, "کد از سمت سرور ارسال شد", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "ارسال کد با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Logger.Log("Throwable: " + t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(LoginActivity.this , Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[] {
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE);
        }
        else {
            sendPhone(phone);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            sendPhone(phone);
        }
    }

    private String getPhone(String input){

        String[] model = {"*" , " " , "-" , "(" , ")"};
        String finalText = input;

        for(String s : model){
            finalText = finalText.replace(s, "");
        }

        return finalText;
    }

    private void initCode(String phone){

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<View, String>(root , "root"),
                new Pair<View, String>(cardView , "card"),
                new Pair<View, String>(inPhone , "inPhone")
        );

        startActivity(
                new Intent(
                        LoginActivity.this , CodeActivity.class
                ).putExtra("phone" , phone) , options.toBundle()
        );

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
    protected void onResume() {
        super.onResume();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };

        registerReceiver(receiver , new IntentFilter(ACTION_FORCE_CLOSE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
