package com.example.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;


public abstract class PercentageViewBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    static final int UNSPECIFIED_INT = Integer.MAX_VALUE;
    static final float UNSPECIFIED_FLOAT = Float.MAX_VALUE;


    //需要联动的View的id
    private int mDependViewId;
    //联动滑动的终点
    private int mDependTarget;
    //联动滑动的起点
    private int mDependStartY;


    /**
     * Is the values prepared to be use
     */
    private boolean isPrepared;

    PercentageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewBehavior);
        mDependViewId = a.getResourceId(R.styleable.ViewBehavior_behavior_dependsOn, 0);
        mDependTarget = a.getDimensionPixelOffset(R.styleable.ViewBehavior_behavior_dependTarget, UNSPECIFIED_INT);
        a.recycle();
    }


    /***
     * 初始化一些数值
     * @param parent
     * @param child
     * @param dependency
     */
    void prepare(CoordinatorLayout parent, V child, View dependency) {
        mDependStartY = (int) dependency.getY();
        isPrepared = true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        // depend on the view that has the same id
        return dependency.getId() == mDependViewId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        // first time, prepare values before continue
        if (!isPrepared) {
            prepare(parent, child, dependency);
        }
        updateView(child, dependency);
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        boolean bool = super.onLayoutChild(parent, child, layoutDirection);
        if (isPrepared) {
            updateView(child, parent.getDependencies(child).get(0));
        }
        return bool;
    }

    /***
     * 根据 dependency 去修改View的状态
     * @param child
     * @param dependency
     */
    void updateView(V child, View dependency) {
        float percent = 0;
        float start = 0;
        float current = 0;
        float end = UNSPECIFIED_INT;
        start = mDependStartY;
        current = dependency.getY();
        end = mDependTarget;
        // need to define target value according to the depend type, if not then skip
        if (end != UNSPECIFIED_INT) {
            percent = Math.abs(current - start) / Math.abs(end - start);
        }

        updateViewWithPercent(child, percent > 1 ? 1 : percent);

    }

    /**
     * 根据百分比去实现子类的状态
     *
     * @param child
     * @param percent
     */
    abstract void updateViewWithPercent(V child, float percent);


}
