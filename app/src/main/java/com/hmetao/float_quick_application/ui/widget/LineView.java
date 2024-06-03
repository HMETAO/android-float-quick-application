package com.hmetao.float_quick_application.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;

@SuppressLint("ViewConstructor")
public class LineView extends CardView {
    private final int width;
    private final int height;
    private final OnChangeViewListener listener;

    public LineView(Context context, int width, int height, OnChangeViewListener listener) {
        super(context);
        this.width = width;
        this.height = height;
        this.listener = listener;
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void initView() {
        // 设置虚化
        setAlpha(0.7f);
        GradientDrawable gradientDrawable = new GradientDrawable();
        // 背景色
        gradientDrawable.setColor(Color.parseColor("#f39c12"));
        // 圆角
        gradientDrawable.setCornerRadius(height);
        // 设置背景
        setBackground(gradientDrawable);
        // 隐藏
        setVisibility(ViewGroup.GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (listener != null) {
                listener.onMaximize(this, event.getRawX());
            }
        }
        return true;
    }

    public interface OnChangeViewListener {
        void onMaximize(View visibleView, float rawX);
    }
}
