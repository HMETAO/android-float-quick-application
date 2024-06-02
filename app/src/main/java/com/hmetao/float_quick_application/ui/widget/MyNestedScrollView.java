package com.hmetao.float_quick_application.ui.widget;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    private boolean scrollable = true;

    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        return scrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {
        return scrollable && super.onInterceptTouchEvent(ev);
    }

    public void setScrollingEnabled(boolean enabled) {
        scrollable = enabled;
    }
}
