package com.hmetao.float_quick_application.core;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hmetao.float_quick_application.R;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
