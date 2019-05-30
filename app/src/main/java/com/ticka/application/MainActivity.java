package com.ticka.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ticka.application.adapters.HomesAdapter;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.core.OptionActivity;
import com.ticka.application.models.home.HomeData;
import com.ticka.application.models.home.HomeModel;
import com.ticka.application.utils.SPUtils;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ticka.application.helpers.UserHelper.KEY_USER_ID;

public class MainActivity extends OptionActivity {

    private static final int REQUEST_CODE = 1080;

    private LinearLayout notFound;
    private RecyclerView recyclerView;
    private HomesAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void init() {

        recyclerView = findViewById(R.id.recyclerView);
        notFound = findViewById(R.id.notFound);
        fab = findViewById(R.id.fab);

        initViews();
    }

    @Override
    protected void initViews() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this , LinearLayoutManager.VERTICAL , false
        ));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if(dx < dy){
                    fab.hide();
                }
                else {
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(
                        new Intent(
                                MainActivity.this , NewActivity.class
                        ),REQUEST_CODE
                );
                overridePendingTransition(R.anim.animation_activity_show , R.anim.animation_activity_hide);
            }
        });

        getHome();
    }

    private void showSnackbar(View view){

        Snackbar.make(view , "ارتباط با سرور برقرار نیست" , Snackbar.LENGTH_INDEFINITE)
                .setAction("برسی مجدد", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getHome();
                    }
                }).setActionTextColor(Color.YELLOW).show();

    }

    private void getHome(){

        APIInterface api = APIClient.getTESTClient();

        int userId = SPUtils.getInstance(this)
                .readInteger(
                        KEY_USER_ID,
                        0
                );

        api.getHomes(userId).enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                if(response.body() != null){
                    List<HomeData> model = response.body().getData();

                    if(model.size() > 0){
                        adapter = new HomesAdapter(MainActivity.this , model);

                        recyclerView.setVisibility(View.VISIBLE);
                        notFound.setVisibility(View.GONE);

                        recyclerView.setAdapter(adapter);
                        runLayoutAnimation(recyclerView);
                    }
                }
                else {
                    showSnackbar(fab);
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                Logger.Log("Throwable: => " + t.getMessage());
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.animation_recycler_view);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "اطلاعات ثبت و برای بازبینی ارسال شد", Toast.LENGTH_SHORT).show();
            getHome();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    private boolean isExit = false;
    @Override
    public void onBackPressed() {

        if(!isExit){
            isExit = true;
            Toast.makeText(this, "برای خروج مجدد ضربه بزنید", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            } , 2000);
        }
        else{
            finish();
        }
    }
}
