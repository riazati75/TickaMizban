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
import com.ticka.application.database.DatabaseHelper;
import com.ticka.application.models.facility.FacilityData;
import com.ticka.application.models.rules.RuleData;

import java.util.ArrayList;
import java.util.List;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.VH>{

    public static final int FACILITY_TYPE = 12;
    public static final int RULES_TYPE = 58;

    private LayoutInflater layoutInflater;
    private List<FacilityData> facilityData = null;
    private List<RuleData> ruleData = null;
    private List<Integer> selectedList = new ArrayList<>();

    public CheckboxAdapter(Context context , int type) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch(type){

            case FACILITY_TYPE:
                this.facilityData = databaseHelper.getFacilities();
                break;

            case RULES_TYPE:
                this.ruleData = databaseHelper.getRules();
                break;
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(layoutInflater.inflate(R.layout.layout_rec_possibilities , viewGroup , false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, @SuppressLint("RecyclerView") final int position) {


        if(facilityData != null){

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
        else if(ruleData != null){
            vh.title.setText(ruleData.get(position).getDescription());

            vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked){
                        selectedList.add(ruleData.get(position).getId());
                    }
                    else {
                        selectedList.remove(ruleData.get(position).getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if(facilityData != null){
            return facilityData.size();
        }
        else {

            if(ruleData != null){
                return ruleData.size();
            }
            else {
                return 0;
            }
        }
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
