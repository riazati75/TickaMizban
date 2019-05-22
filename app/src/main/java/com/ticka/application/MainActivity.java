package com.ticka.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
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

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends OptionActivity {

    private static final int REQUEST_CODE = 1080;

    private LinearLayout notFound;
    private RecyclerView recyclerView;
    private HomesAdapter adapter;
    private FloatingActionButton fab;
    private boolean isConnect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void init() {

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.root) , true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnect){
                    startActivityForResult(
                            new Intent(
                                    MainActivity.this , NewActivity.class
                            ),REQUEST_CODE
                    );
                }
                else {
                    showSnackbar(v);
                }
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
        api.getHomes().enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                if(response.body() != null){
                    isConnect = true;
                    List<HomeData> model = response.body().getData();
                    adapter = new HomesAdapter(MainActivity.this , model);

                    recyclerView.setVisibility(View.VISIBLE);
                    notFound.setVisibility(View.GONE);

                    recyclerView.setAdapter(adapter);
                    runLayoutAnimation(recyclerView);
                }
                else {
                    isConnect = false;
                    Logger.Log("Error: => " );
                    Toast.makeText(MainActivity.this, "خطای شناخته نشده ای رخ داده است", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                isConnect = false;
                Logger.Log("Throwable: => " + t.getMessage());
                showSnackbar(fab);
            }
        });
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.animation_recycler_view);

        recyclerView.setLayoutAnimation(controller);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "اطلاعات ثبت و برای بازبینی ارسال شد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
