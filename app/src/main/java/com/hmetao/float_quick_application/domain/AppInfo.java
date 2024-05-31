package com.hmetao.float_quick_application.domain;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class AppInfo {

    public String packageName;

    public Drawable appIcon;
    public String appName;

    public AppInfo() {
    }

    public AppInfo(String packageName, Drawable appIcon, String appName) {
        this.packageName = packageName;
        this.appIcon = appIcon;
        this.appName = appName;
    }

    @NonNull
    @Override
    public String toString() {
        return "ApplicationInfo{" +
                "packageName='" + packageName + '\'' +
                ", appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                '}';
    }
}
