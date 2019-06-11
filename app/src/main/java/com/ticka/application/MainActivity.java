package com.ticka.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private static final int REQUEST_CODE = 10;

    private LinearLayout notFound;
    private RecyclerView recyclerView;
    private TextView titleDescription;
    private HomesAdapter adapter;
    private ImageView refresh;
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void init() {

        titleDescription = findViewById(R.id.titleDescription);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout = findViewById(R.id.drawerLayout);
        notFound = findViewById(R.id.notFound);
        refresh = findViewById(R.id.refresh);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "در حال برسی اطلاعات داخلی", Toast.LENGTH_LONG).show();

                startActivityForResult(
                        new Intent(
                                MainActivity.this , NewActivity.class
                        ),REQUEST_CODE
                );

                overridePendingTransition(R.anim.animation_activity_show , R.anim.animation_activity_hide);
            }
        });

        initViews();
        setNavigationView();
    }

    private void setNavigationView(){

        navigationView.setCheckedItem(R.id.m1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                titleDescription.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                titleDescription.setHorizontallyScrolling(true);
                titleDescription.setSelected(true);

                switch(id){

                    case R.id.m1:
                        drawerLayout.closeDrawer(GravityCompat.END);

                        break;

                    case R.id.m2:
                        drawerLayout.closeDrawer(GravityCompat.END);

                        break;

                    case R.id.m3:
                        drawerLayout.closeDrawer(GravityCompat.END);

                        break;

                    default: drawerLayout.closeDrawer(GravityCompat.END);
                }
                return true;
            }
        });

        navigationView.setMeasureAllChildren(true);

    }

    @Override
    protected void initViews() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this , RecyclerView.VERTICAL , false
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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHome();
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
                    else {
                        Toast.makeText(MainActivity.this, "شما هنوز اقامتگاهی ثبت نکردید", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    showSnackbar(fab);
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "خطا در اتصال به اینترنت", Toast.LENGTH_SHORT).show();
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
        super.onActivityResult(requestCode, resultCode, data);
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
