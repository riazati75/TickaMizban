package com.ticka.application.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;
import com.ticka.application.R;
import com.ticka.application.fragments.CapacityFragment;
import com.ticka.application.fragments.GeneralDetailsFragment;
import com.ticka.application.fragments.MinorDetailsFragment;
import com.ticka.application.fragments.PossibilitiesFragment;
import com.ticka.application.fragments.RulesFragment;
import com.ticka.application.fragments.UploadPhotoFragment;
import com.ticka.application.fragments.UserRulesFragment;

public class StepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "messageResourceId";

    public StepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {

        switch (position){

            case 0:
                RulesFragment step1 = new RulesFragment();
                Bundle b1 = new Bundle();
                b1.putInt(CURRENT_STEP_POSITION_KEY, position);
                step1.setArguments(b1);
                return step1;

            case 1:
                GeneralDetailsFragment step2 = new GeneralDetailsFragment();
                Bundle b2 = new Bundle();
                b2.putInt(CURRENT_STEP_POSITION_KEY, position);
                step2.setArguments(b2);
                return step2;

            case 2:
                UploadPhotoFragment step3 = new UploadPhotoFragment();
                Bundle b3 = new Bundle();
                b3.putInt(CURRENT_STEP_POSITION_KEY, position);
                step3.setArguments(b3);
                return step3;

            case 3:
                MinorDetailsFragment step4 = new MinorDetailsFragment();
                Bundle b4 = new Bundle();
                b4.putInt(CURRENT_STEP_POSITION_KEY, position);
                step4.setArguments(b4);
                return step4;

            case 4:
                CapacityFragment step5 = new CapacityFragment();
                Bundle b5 = new Bundle();
                b5.putInt(CURRENT_STEP_POSITION_KEY, position);
                step5.setArguments(b5);
                return step5;

            case 5:
                PossibilitiesFragment step6 = new PossibilitiesFragment();
                Bundle b6 = new Bundle();
                b6.putInt(CURRENT_STEP_POSITION_KEY, position);
                step6.setArguments(b6);
                return step6;

            case 6:
                UserRulesFragment step7 = new UserRulesFragment();
                Bundle b7 = new Bundle();
                b7.putInt(CURRENT_STEP_POSITION_KEY, position);
                step7.setArguments(b7);
                return step7;

            case 7:
                UserRulesFragment step8 = new UserRulesFragment();
                Bundle b8 = new Bundle();
                b8.putInt(CURRENT_STEP_POSITION_KEY, position);
                step8.setArguments(b8);
                return step8;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {

        switch (position){

            case 0: return new StepViewModel.Builder(context)
                        .setTitle("قوانین همکاری")
                        .setSubtitle("قوانین و مقررات تیکا")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 1: return new StepViewModel.Builder(context)
                        .setTitle("اطلاعات کلی")
                        .setSubtitle("اطلاعات اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 2:
                return new StepViewModel.Builder(context)
                        .setTitle("تصاویر")
                        .setSubtitle("تصاویر اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();
            case 3: return new StepViewModel.Builder(context)
                        .setTitle("اطلاعات جزعی")
                        .setSubtitle("اطلاعات تکمیلی اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 4: return new StepViewModel.Builder(context)
                        .setTitle("ظرفیت")
                        .setSubtitle("اطلاعات مربوط به نفرات")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 5: return new StepViewModel.Builder(context)
                        .setTitle("امکانات")
                        .setSubtitle("امکانات موجود در اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 6: return new StepViewModel.Builder(context)
                        .setTitle("قوانین")
                        .setSubtitle("قوانین اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            case 7: return new StepViewModel.Builder(context)
                        .setTitle("قیمت گذاری")
                        .setSubtitle("قیمت گذاری برای رزرو اقامتگاه")
                        .setNextButtonEndDrawableResId(R.drawable.icon_arrow_right)
                        .setBackButtonStartDrawableResId(R.drawable.icon_arrow_left)
                        .create();

            default: return null;
        }
    }
}
