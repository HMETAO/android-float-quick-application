package com.hmetao.float_quick_application.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        beforeInitView();
        initView();
        afterInitView();
    }
    private void afterInitView() {
    }

    protected void beforeInitView() {

    }

    protected void initView() {
    }

    protected abstract View getRootView();
}
