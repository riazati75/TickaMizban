package com.ticka.application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ticka.application.R;

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private int itemCount = 1;
    private OnItemClicked onItemClicked;

    public AddPhotoAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        notifyDataSetChanged();
    }

    public void setOnItemClicked(OnItemClicked onItemClicked){
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int type) {
        return new ViewHolder(layoutInflater.inflate(R.layout.layout_recycler_gallery , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        viewHolder.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(onItemClicked != null){
                    onItemClicked.onClicked(position , viewHolder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo , upload;
        public TextView txt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            photo = itemView.findViewById(R.id.photo);
            upload = itemView.findViewById(R.id.upload);
            txt = itemView.findViewById(R.id.txtValue);

        }
    }

    public interface OnItemClicked {
        void onClicked(int position , ViewHolder viewHolder);
    }
}
