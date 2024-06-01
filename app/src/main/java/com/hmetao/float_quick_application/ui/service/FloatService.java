package com.hmetao.float_quick_application.ui.service;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

import com.hmetao.float_quick_application.domain.AppInfo;
import com.hmetao.float_quick_application.help.WindowManagerHelper;
import com.hmetao.float_quick_application.ui.widget.ApplicationListView;
import com.hmetao.float_quick_application.utils.DensityUtil;

import java.util.List;

public class FloatService extends Service {

    private static final String TAG = FloatService.class.getSimpleName();
    private NestedScrollView floatRootView;
    private WindowManager wm;
    private List<AppInfo> apps;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListRootView();
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindow() {
        // 获取wm
        wm = WindowManagerHelper.instance.getWindowManager(this);
        WindowManager.LayoutParams windowLayoutParams = WindowManagerHelper.instance.
                buildWindowLayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(this, 54 * 5));
        wm.addView(floatRootView, windowLayoutParams);
    }

    private NestedScrollView buildApplicationListRootView() {
        NestedScrollView nestedScrollView = new NestedScrollView(this);
        apps = getAppInfo(getBaseContext());
        ApplicationListView applicationListView = new ApplicationListView(this, apps);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        // 设置根root的长宽
        nestedScrollView.addView(applicationListView);
        return nestedScrollView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatRootView != null) {
            wm.removeView(floatRootView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
