package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticka.application.R;

public class ReviewAdaptert extends RecyclerView.Adapter<ReviewAdaptert.Holder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] titles = {
            "عنوان اقامتگاه:"
    };

    public ReviewAdaptert(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new Holder(layoutInflater.inflate(R.layout.layout_recycler_review , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtTitle.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView txtTitle , txtValue;

        Holder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }

}
