package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ticka.application.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Holder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] values;
    private String[] titles = {
            "عنوان اقامتگاه:",
            "استان:",
            "شهر:",
            "آدرس:",
            "معرفی اقامتگاه:",
            "نوع ساختمان:",
            "نوع منطقه ای:",
            "تعداد اتاق:",
            "متراژ زمین:",
            "متراژ ساختمان:",
            "ظرفیت استاندارد:",
            "حداکثر ظرفیت:",
            "تعداد تخت 1 نفره:",
            "تعداد تخت 2 نفره:",
            "تعداد رخت خواب اضافه:",
            "توضیحات ظرفیت:",
            "امکانات:",
            "توضیحات امکانات:",
            "قوانین:",
            "توضیحات قوانین:"
    };

    public ReviewAdapter(Context context , String[] values) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = values;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new Holder(layoutInflater.inflate(R.layout.layout_recycler_review , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.txtTitle.setText(titles[position]);
        holder.txtValue.setText(values[position]);
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
