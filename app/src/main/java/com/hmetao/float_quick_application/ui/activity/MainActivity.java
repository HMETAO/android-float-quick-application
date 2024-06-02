package com.hmetao.float_quick_application.ui.activity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
    }

    private void checkPermission() {
        if (checkOverlayDisplayPermission()) {
            // 开启悬浮窗服务
            startService(new Intent(this, FloatService.class));
            finish();
        } else {
            showNeedFloatPermissionDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检查权限
        checkPermission();
    }

    private boolean checkOverlayDisplayPermission() {
        // API23以后需要检查权限
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        } else {
            return true;
        }
    }

    @Override
    protected View getRootView() {
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mainBinding.getRoot();
    }

    private void showNeedFloatPermissionDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setMessage("该应用需要打开显示在上层的权限，请到 应用信息 -> 权限 中去授权")
                .setPositiveButton("OK", (dialog2, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}