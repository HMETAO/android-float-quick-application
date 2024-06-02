package com.hmetao.float_quick_application.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 2019/1/2.
 * android_shuai@163.com
 */
public class PermissionUtil {


    // 檢查是否有權限
//    public static boolean isNeedRequestPermission(Activity activity) {
//        return isNeedRequestPermission(activity,Manifest.permission.READ_MEDIA_AUDIO);
//    }

    private static boolean isNeedRequestPermission(Activity activity, String... permissions) {
        List<String> mPermissionListDenied = new ArrayList<>();
        for (String permission : permissions) {
            int result = checkPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                mPermissionListDenied.add(permission);
            }
        }
        if (!mPermissionListDenied.isEmpty()) {
            String[] pears = new String[mPermissionListDenied.size()];
            pears = mPermissionListDenied.toArray(pears);
            ActivityCompat.requestPermissions(activity, pears, 0);
            return true;
        } else {
            return false;
        }
    }

    private static int checkPermission(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission);
    }


}
