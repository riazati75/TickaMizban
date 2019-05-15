package com.ticka.application.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.ticka.application.R;
import com.ticka.application.adapters.CheckboxAdapter;

public class PossibilitiesFragment extends Fragment implements BlockingStep {

    private Context context;
    private RecyclerView recyclerView;
    private CheckboxAdapter adapter;

    public PossibilitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_possibilities, container, false);
        context = container.getContext();

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(
                context, 2
        ));
        recyclerView.setHasFixedSize(true);

        adapter = new CheckboxAdapter(context , null);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
