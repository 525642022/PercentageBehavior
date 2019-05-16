package com.example.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


public class AlphaViewBehavior extends PercentageViewBehavior<View> {

    private float mStartAlpha;
    private float targetAlpha;

    public AlphaViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        // setting values
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewBehavior);
        targetAlpha = a.getFloat(R.styleable.ViewBehavior_behavior_targetAlpha, UNSPECIFIED_FLOAT);
        a.recycle();
    }


    @Override
    void prepare(CoordinatorLayout parent, View child, View dependency) {
        super.prepare(parent, child, dependency);
        mStartAlpha = child.getAlpha();
    }

    @Override
    void updateViewWithPercent(View child, float percent) {
        if (percent < 0.5) {
            if (targetAlpha != UNSPECIFIED_FLOAT) {
                child.setAlpha(mStartAlpha + (targetAlpha - mStartAlpha) * percent * 2);
            }
        }
        child.requestLayout();
    }


}
