package com.hmetao.float_quick_application.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hmetao.float_quick_application.domain.AppInfo;

import java.util.List;
import java.util.stream.Collectors;

public class AppUtil {
    public static List<AppInfo> getAppInfo(Context context) {
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        return packages.stream().filter(packageInfo -> (packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                .map(packageInfo -> {
                    AppInfo info = new AppInfo();
                    info.appIcon = pm.getApplicationIcon(packageInfo);
                    info.appName = pm.getApplicationLabel(packageInfo).toString();
                    info.packageName = packageInfo.packageName;
                    return info;
                }).collect(Collectors.toList());
    }
}