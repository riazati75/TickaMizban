package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ticka.application.R;
import com.ticka.application.api.APIClient;
import com.ticka.application.api.APIInterface;
import com.ticka.application.core.Logger;
import com.ticka.application.models.home.HomeData;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomesAdapter extends RecyclerView.Adapter<HomesAdapter.Holder> {

     private Context context;
     private LayoutInflater layoutInflater;
    private List<HomeData> homeData;

    public HomesAdapter(Context context , List<HomeData> homeData) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.homeData = homeData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(layoutInflater.inflate(R.layout.layout_recycler_homs , viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        setImage(holder.photo , homeData.get(position).getGallery().get(position).getSrc());

    }

    private void setImage(final ImageView image , String imageId){

        APIInterface api = APIClient.getCDNClient();
        api.getPhoto(imageId).enqueue(new Callback<File>() {
            @Override
            public void onResponse(Call<File> call, Response<File> response) {

                if(response.isSuccessful()){

                    Picasso.with(context)
                            .load(response.body())
                            .error(android.R.drawable.stat_notify_error)
                            .placeholder(android.R.drawable.stat_sys_download)
                            .into(image);
                }
                else {
                    Logger.Log("Response: => " + response.message());
                }

            }

            @Override
            public void onFailure(Call<File> call, Throwable t) {
                Logger.Log("ThrowableAdapter: => " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeData.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView photo;

        Holder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
        }
    }

}
