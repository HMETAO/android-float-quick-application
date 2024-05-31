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
        // loadData
        loadData();
    }

    private void loadData() {
        apps = getAppInfo(getBaseContext());
        Log.d(TAG, "apps: " + apps);
    }

    @Override
    public void initView() {

//        // 启动悬浮窗
//        WindowManager manager = getWindowManager();
//        WindowManager.LayoutParams windowManageParams = new WindowManager.LayoutParams();
//        windowManageParams.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? TYPE_APPLICATION_OVERLAY : TYPE_PHONE;
        ApplicationListView view = buildApplicationListView();
        mainBinding.getRoot().addView(view);
//        manager.addView(view, windowManageParams);
    }

    @Override
    protected View getRootView() {
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mainBinding.getRoot();
    }

    private ApplicationListView buildApplicationListView() {
        ApplicationListView applicationListView = new ApplicationListView(this, apps);
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        return applicationListView;
    }
}