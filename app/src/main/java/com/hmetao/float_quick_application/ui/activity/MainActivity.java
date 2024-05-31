package com.hmetao.float_quick_application.ui.activity;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
import static android.view.WindowManager.LayoutParams.TYPE_PHONE;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hmetao.float_quick_application.core.BaseActivity;
import com.hmetao.float_quick_application.databinding.ActivityMainBinding;
import com.hmetao.float_quick_application.domain.AppInfo;
import com.hmetao.float_quick_application.ui.compose.ApplicationListView;
import com.hmetao.float_quick_application.ui.service.FloatService;

import java.util.List;

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