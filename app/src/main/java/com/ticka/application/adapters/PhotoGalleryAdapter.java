package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ticka.application.R;

public class PhotoGalleryAdapter extends RecyclerView.Adapter<PhotoGalleryAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private int itemCount = 1;

    public PhotoGalleryAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(layoutInflater.inflate(R.layout.layout_recycler_gallery , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCount = itemCount + 1;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photo , upload;
        TextView txt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            upload = itemView.findViewById(R.id.upload);
            txt = itemView.findViewById(R.id.txt);

        }
    }
}
