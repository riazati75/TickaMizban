package com.ticka.application.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ticka.application.R;

import java.util.List;

public class StateCityAdapter extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;
    private List<String> objects;
    private Typeface typeface;

    public StateCityAdapter(Context context, List<String> objects) {
        super(context, R.layout.layout_spinner_state_city, objects);
        this.objects = objects;
        this.typeface = ResourcesCompat.getFont(context, R.font.yekan_mobile_regular);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTypeface(typeface);
        return textView;
    }

    @NonNull
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setTypeface(typeface);
        return textView;
    }
}
