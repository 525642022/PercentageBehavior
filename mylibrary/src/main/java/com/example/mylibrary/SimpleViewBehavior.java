package com.example.mylibrary;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


public class SimpleViewBehavior extends PercentageViewBehavior<View> {
    //开始的信息
    private int mStartX;
    private int mStartY;
    private int mStartWidth;
    private int mStartHeight;

    //结束的信息
    private int targetX;
    private int targetY;
    private int targetWidth;
    private int targetHeight;


    public SimpleViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        // setting values
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewBehavior);
        targetX = a.getDimensionPixelOffset(R.styleable.ViewBehavior_behavior_targetX, UNSPECIFIED_INT);
        targetY = a.getDimensionPixelOffset(R.styleable.ViewBehavior_behavior_targetY, UNSPECIFIED_INT);
        targetWidth = a.getDimensionPixelOffset(R.styleable.ViewBehavior_behavior_targetWidth, UNSPECIFIED_INT);
        targetHeight = a.getDimensionPixelOffset(R.styleable.ViewBehavior_behavior_targetHeight, UNSPECIFIED_INT);
        a.recycle();
    }


    @Override
    void prepare(CoordinatorLayout parent, View child, View dependency) {
        super.prepare(parent, child, dependency);

        mStartX = (int) child.getX();
        mStartY = (int) child.getY();
        mStartWidth = child.getWidth();
        mStartHeight = child.getHeight();
        // if parent fitsSystemWindows is true, add status bar height to target y if specified
        if (Build.VERSION.SDK_INT > 16 && parent.getFitsSystemWindows() && targetY != UNSPECIFIED_INT) {
            int result = 0;
            Resources resources = parent.getContext().getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId);
            }
            targetY += result;
        }
    }

    @Override
    void updateViewWithPercent(View child, float percent) {
        float scaleWidth = mStartWidth + ((targetWidth - mStartWidth) * percent);
        float scaleHeight = mStartHeight + ((targetHeight - mStartHeight) * percent);
        if (targetWidth != UNSPECIFIED_INT || targetHeight != UNSPECIFIED_INT) {
            child.setScaleX(scaleWidth / mStartWidth);
            child.setScaleY(scaleHeight / mStartHeight);
        }
        if (percent < 0.5) {
            float newX = targetX == UNSPECIFIED_INT ? 0 : (targetX - mStartX) * percent * 2;
            float newWidth = mStartWidth + ((targetWidth - mStartWidth) * percent * 2);
            newX -= (mStartWidth - newWidth) / 2;
            child.setTranslationX(newX);
        } else {
            float newY = targetY == UNSPECIFIED_INT ? 0 : (float) ((targetY - mStartY) * (percent - 0.5) * 2);
            float newHeight = (float) (mStartHeight + ((targetHeight - mStartHeight) * (percent - 0.5) * 2));
            newY -= (mStartHeight - newHeight) / 2;
            child.setTranslationY(newY);
        }
        child.requestLayout();
    }
}
