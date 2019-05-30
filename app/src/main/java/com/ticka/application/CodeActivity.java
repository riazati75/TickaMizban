package com.ticka.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.custom.verification.CodeVerification;
import com.ticka.application.custom.verification.OnCompletionListener;
import com.ticka.application.helpers.UserHelper;
import com.ticka.application.models.callback.LoginCallback;
import com.ticka.application.utils.JSONUtils;
import com.ticka.application.utils.TimeLoader;

import org.json.JSONObject;

import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ticka.application.SplashActivity.ACTION_FORCE_CLOSE;
import static com.ticka.application.service.SMSService.ACTION_CODE_RECEIVED;

public class CodeActivity extends OptionActivity {

    private CodeVerification verification;
    private FrameLayout root;
    private CardView cardView;
    private BroadcastReceiver receiver , close;
    private UserHelper userHelper;
    private String phone;
    private TimeLoader loader = TimeLoader.getTimeLoader();
    private TextView timer , resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        setupNotificationBar();
        init();
    }

    @Override
    protected void init() {

        userHelper = UserHelper.getInstance(this);
        phone = getIntent().getStringExtra("phone");
        verification = findViewById(R.id.otpView);
        root = findViewById(R.id.frameLayout);
        cardView = findViewById(R.id.cardView);
        timer = findViewById(R.id.timer);
        resend = findViewById(R.id.resend);
        setTimer();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String code = intent.getStringExtra("code");
                verification.setText(code);
            }
        };

        initViews();
    }

    private void sendPhone(String phone){

        setTimer();
        JSONObject object = JSONUtils.getJsonPhone(phone);

        RequestBody body = RequestBody.create(
                MediaType.parse(APIClient.BODY_TEXT_PLAIN_TYPE),
                object.toString());

        APIInterface api = APIClient.getAPIClient();
        api.login(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    Toast.makeText(CodeActivity.this, "کد از سمت سرور ارسال شد", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CodeActivity.this, "ارسال کد با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                    loader.stopTimer();
                    String text = "کد را دریافت نکردید؟ 00:00" + "\nمجدد امتحان کنید";
                    timer.setText(text);
                    resend.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CodeActivity.this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
                loader.stopTimer();
                String text = "کد را دریافت نکردید؟ 00:00" + "\nمجدد امتحان کنید";
                timer.setText(text);
                resend.setEnabled(true);
            }
        });
    }

    @Override
    protected void initViews() {

        verification.setAnimationEnable(true);
        verification.setCompletionListener(new OnCompletionListener() {
            @Override
            public void onFinishEnterCode(String code) {
                verificationCode(code);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhone(phone);
            }
        });
    }

    private void verificationCode(String code){

        setTimer();

        JSONObject object = JSONUtils.getJsonCode(phone , code);
        RequestBody body = RequestBody.create(
                MediaType.parse(APIClient.BODY_TEXT_PLAIN_TYPE),
                object.toString());

        APIInterface api = APIClient.getAPIClient();
        api.verificationCode(body).enqueue(new Callback<LoginCallback>() {
            @Override
            public void onResponse(Call<LoginCallback> call, Response<LoginCallback> response) {

                Logger.Log("Response: " + response.message());

                if(response.isSuccessful()){

                    if(response.body() != null){
                        loginSuccess(response.body());
                    }
                }
                else {
                    loginFailed();
                }
            }

            @Override
            public void onFailure(Call<LoginCallback> call, Throwable t) {
                Logger.Log("Throwable: " + t.getMessage());
                Toast.makeText(CodeActivity.this, "خطا در برقراری ارتباط با سرور", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginSuccess(LoginCallback callback){

        Random random = new Random();
        userHelper.setUserId(random.nextInt(999));
        userHelper.setUserPhone(phone);
        userHelper.setUserJoined(true);
        userHelper.setUserToken(callback.getToken());
        userHelper.setRefreshToken(callback.getRefreshToken());

        cardView.animate().alpha(0).setDuration(800).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CodeActivity.this, "ورود با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                initLogin();
            }
        } , 1100);
    }

    private void loginFailed(){
        Toast.makeText(this, "کد اشتباه است", Toast.LENGTH_SHORT).show();
        verification.setText("");
    }

    private void initLogin(){

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<View, String>(root , "root")
        );

        startActivity(
                new Intent(
                        CodeActivity.this , MainActivity.class
                ), options.toBundle()
        );

        closeActivity();
    }

    private void closeActivity(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(
                        new Intent(ACTION_FORCE_CLOSE)
                );
            }
        } , 1000);

    }

    private void setTimer(){

        resend.setEnabled(false);

        final String textBase = "کد را دریافت نکردید؟ ";

        loader.setTimeLoader(20);
        loader.startTimer(new TimeLoader.OnTimeChanged() {
            @Override
            public void onChange(int millisecond) {
                if(millisecond > 9){
                    String text = textBase +  "00:"  + millisecond;
                    timer.setText(text);
                }
                else {
                    String text = textBase +  "00:0"  + millisecond;
                    timer.setText(text);
                }
            }

            @Override
            public void onFinish() {
                resend.setEnabled(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver , new IntentFilter(ACTION_CODE_RECEIVED));

        close = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(close , new IntentFilter(ACTION_FORCE_CLOSE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(close);
        unregisterReceiver(receiver);
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
