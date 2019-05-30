package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ticka.application.R;
import com.ticka.application.models.home.HomeData;

import java.util.List;

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

        holder.txtTitle.setText(homeData.get(position).getName());
        holder.txtDesc.setText(homeData.get(position).getDescription());

        setImage(holder.photo , homeData.get(position).getGallery().get(0).getSrc());
    }

    private void setImage(final ImageView image , String imageId){

        Picasso.with(context)
                .load("http://cdn.ticka.com/file/" + imageId)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(image);
    }

    @Override
    public int getItemCount() {
        return homeData.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView txtTitle , txtDesc;

        Holder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photo);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }

}
