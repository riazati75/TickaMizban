package com.ticka.application;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.transition.Fade;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.ticka.application.core.OptionActivity;
import com.ticka.application.helpers.UserHelper;

public class SplashActivity extends OptionActivity {

    public static final String ACTION_FORCE_CLOSE = "forceClose";

    private ConstraintLayout root;
    private ImageView photo;
    private Button button;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupNotificationBar();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.root) , true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        init();
    }

    @Override
    protected void init() {
        userHelper = UserHelper.getInstance(this);

        root   = findViewById(R.id.root);
        photo  = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        initViews();
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
