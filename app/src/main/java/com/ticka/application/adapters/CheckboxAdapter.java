package com.ticka.application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ticka.application.R;
import com.ticka.application.models.facility.FacilityData;

import java.util.ArrayList;
import java.util.List;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.VH>{

    private LayoutInflater layoutInflater;
    private List<FacilityData> facilityData;
    private List<Integer> selectedList = new ArrayList<>();

    public CheckboxAdapter(Context context , List<FacilityData> facilityData) {
        this.facilityData = facilityData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(layoutInflater.inflate(R.layout.layout_rec_possibilities , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, @SuppressLint("RecyclerView") final int position) {

        vh.title.setText(facilityData.get(position).getName());

        vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    selectedList.add(facilityData.get(position).getId());
                }
                else {
                    selectedList.remove(facilityData.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return facilityData.size();
    }

    public List<Integer> getSelectedList() {
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
