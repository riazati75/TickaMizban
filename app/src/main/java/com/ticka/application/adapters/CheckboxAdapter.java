package com.ticka.application.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ticka.application.R;

import java.util.ArrayList;
import java.util.List;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.VH>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> list;
    private List<String> selectedList = new ArrayList<>();

    public CheckboxAdapter(Context context , @Nullable List<String> list) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(layoutInflater.inflate(R.layout.layout_rec_possibilities , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, final int position) {

        final String text = "آیتم " + position;

        vh.title.setText(text);

        vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    selectedList.add(text);
                }
                else {
                    selectedList.remove(text);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return list.size();
        return 12;
    }

    public List<String> getSelectedList() {
        return selectedList;
    }

    class VH extends RecyclerView.ViewHolder {

        private CheckBox checkbox;
        private TextView title;

        VH(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            title    = itemView.findViewById(R.id.title);
        }
    }
}
