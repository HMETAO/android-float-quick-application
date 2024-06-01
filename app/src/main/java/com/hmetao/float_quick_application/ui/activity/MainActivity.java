package com.hmetao.float_quick_application.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hmetao.float_quick_application.core.BaseActivity;
import com.hmetao.float_quick_application.databinding.ActivityMainBinding;
import com.hmetao.float_quick_application.ui.service.FloatService;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 开启悬浮窗服务
        startService(new Intent(this, FloatService.class));
    }

    @Override
    protected View getRootView() {
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mainBinding.getRoot();
    }

}