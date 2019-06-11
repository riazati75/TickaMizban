package com.ticka.application.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ticka.application.R;

public class ValueChanger extends RelativeLayout {

    private static final int DELAY = 200;

    private Context context;
    private Handler changer;
    private AutoPlussing autoPlussing;
    private AutoDecrease autoDecrease;
    private TextView count;
    private ImageView plus , mines;
    private String minCountError = null;
    private String maxCountError = null;
    private String accompanyingText = null;
    private int defaultValue;
    private int countValue;
    private int minValue;
    private int maxValue;
    private Drawable backgroundShape = null;
    private Drawable iconPlus = null;
    private Drawable iconMines = null;

    public ValueChanger(Context context) {
        super(context);
        init(context,null,0);
    }

    public ValueChanger(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public ValueChanger(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        View root = inflate(context , R.layout.widget_value_changer , this);
        this.context = context;

        if(attrs != null){
            initAttribute(context , attrs , defStyleAttr);
        }

        initViews(root);
    }

    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr){

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs , R.styleable.ValueChanger, defStyleAttr,0);

        this.defaultValue      = typedArray.getInt(R.styleable.ValueChanger_vcDefaultValue , 0);
        this.minValue          = typedArray.getInt(R.styleable.ValueChanger_vcMinValue , 0);
        this.maxValue          = typedArray.getInt(R.styleable.ValueChanger_vcMaxValue , 100);
        this.minCountError     = typedArray.getString(R.styleable.ValueChanger_vcMinCountError);
        this.maxCountError     = typedArray.getString(R.styleable.ValueChanger_vcMaxCountError);
        this.accompanyingText  = typedArray.getString(R.styleable.ValueChanger_vcAccompanyingText);
        this.backgroundShape   = typedArray.getDrawable(R.styleable.ValueChanger_vcBackgroundShape);
        this.iconPlus          = typedArray.getDrawable(R.styleable.ValueChanger_vcPlusIcon);
        this.iconMines         = typedArray.getDrawable(R.styleable.ValueChanger_vcMinesIcon);

        typedArray.recycle();

        if(this.accompanyingText == null){
            this.accompanyingText = "";
        }

        if(this.minCountError == null){
            this.minCountError = "مقدار وارد شده نمیتواند از " + this.minValue + accompanyingText + " کمتر باشد";
        }

        if(this.maxCountError == null){
            this.maxCountError = "مقدار وارد شده نمیتواند از " + this.maxValue + accompanyingText + " بیشتر باشد";
        }

        if(defaultValue < minValue){
            defaultValue = minValue;
        }

        if(defaultValue > maxValue){
            defaultValue = maxValue;
        }

        countValue = defaultValue;
    }

    private void initViews(View view){

        this.changer = new Handler();
        this.autoPlussing = new AutoPlussing();
        this.autoDecrease = new AutoDecrease();

        RelativeLayout root = view.findViewById(R.id.root);
        LinearLayout background = view.findViewById(R.id.background);
        this.count = view.findViewById(R.id.count);
        this.plus  = view.findViewById(R.id.plus);
        this.mines = view.findViewById(R.id.mines);

        int width  = this.getWidth();
        int height = this.getHeight();

        if(width > 0 && height > 0){
            LayoutParams params = new LayoutParams(width , height);
            root.setLayoutParams(params);
        }

        if(iconPlus != null){
            this.plus.setImageDrawable(iconPlus);
        }
        if(iconMines != null){
            this.mines.setImageDrawable(iconMines);
        }
        if(backgroundShape != null){
            background.setBackground(backgroundShape);
        }

        this.count.setText(getCount(defaultValue));

        initPlusButton();
        initMinesButton();
    }

    private String getCount(int count){
        return count + accompanyingText;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initPlusButton(){

        this.plus.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                switch(action){

                    case MotionEvent.ACTION_DOWN:
                        changer.post(autoPlussing);
                        break;

                    case MotionEvent.ACTION_UP:
                        changer.removeCallbacks(autoPlussing);
                        break;
                }

                return false;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initMinesButton(){

        this.mines.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();

                switch(action){

                    case MotionEvent.ACTION_DOWN:
                        changer.post(autoDecrease);
                        break;

                    case MotionEvent.ACTION_UP:
                        changer.removeCallbacks(autoDecrease);
                        break;
                }

                return false;
            }
        });

    }

    public void setBackgroundShape(@DrawableRes int backgroundShape) {
        this.backgroundShape = getResources().getDrawable(backgroundShape);
        invalidate();
    }

    public void setBackgroundShape(Drawable backgroundShape) {
        this.backgroundShape = backgroundShape;
        invalidate();
    }

    public void setIconPlus(@DrawableRes int iconPlus) {
        this.iconPlus = getResources().getDrawable(iconPlus);
        invalidate();
    }

    public void setIconPlus(Drawable iconPlus) {
        this.iconPlus = iconPlus;
        invalidate();
    }

    public void setIconMines(@DrawableRes int iconMines) {
        this.iconMines = getResources().getDrawable(iconMines);
        invalidate();
    }

    public void setIconMines(Drawable iconMines) {
        this.iconMines = iconMines;
        invalidate();
    }

    public void setDefaultValue(int defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public void setValue(int countValue) {
        this.countValue = countValue;
    }

    public int getValue() {
        return countValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMinCountError(String minCountError) {
        this.minCountError = minCountError;
    }

    public String getMinCountError() {
        return minCountError;
    }

    public void setMaxCountError(String maxCountError) {
        this.maxCountError = maxCountError;
    }

    public String getMaxCountError() {
        return this.maxCountError;
    }

    private class AutoPlussing implements Runnable {

        @Override
        public void run() {

            if(countValue >= maxValue){
                changer.removeCallbacks(autoPlussing);
                Toast.makeText(context, maxCountError, Toast.LENGTH_SHORT).show();
                return;
            }

            countValue += 1;
            count.setText(getCount(countValue));
            invalidate();

            changer.postDelayed(autoPlussing, DELAY);
        }
    }

    private class AutoDecrease implements Runnable {

        @Override
        public void run() {

            if(countValue <= minValue){
                changer.removeCallbacks(autoDecrease);
                Toast.makeText(context, minCountError, Toast.LENGTH_SHORT).show();
                return;
            }

            countValue -= 1;
            count.setText(getCount(countValue));
            invalidate();

            changer.postDelayed(autoDecrease, DELAY);
        }
    }
}
