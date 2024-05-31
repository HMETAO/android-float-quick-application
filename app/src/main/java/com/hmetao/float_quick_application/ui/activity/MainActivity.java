package com.hmetao.float_quick_application.ui.activity;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
import static android.view.WindowManager.LayoutParams.TYPE_PHONE;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

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

import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mainBinding;
    private List<AppInfo> apps;


    @Override
    protected void beforeInitView() {
        // 获取apps
        apps = getAppInfo(getBaseContext());
        Log.d(TAG, "apps: " + apps);
    }

    @Override
    public void initView() {
        // 构建悬浮窗主view
        ApplicationListView view = buildApplicationListView();

        LinearLayout root = mainBinding.getRoot();
        root.addView(view);
    }

    @Override
    protected View getRootView() {
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mainBinding.getRoot();
    }

    private ApplicationListView buildApplicationListView() {
        ApplicationListView applicationListView = new ApplicationListView(this, apps);
        // 设置布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        applicationListView.setLayoutParams(params);
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        return applicationListView;
    }
}