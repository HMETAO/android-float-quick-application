package com.hmetao.float_quick_application.ui.service;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hmetao.float_quick_application.domain.AppInfo;
import com.hmetao.float_quick_application.help.WindowManagerHelper;
import com.hmetao.float_quick_application.ui.widget.ApplicationItemView;
import com.hmetao.float_quick_application.ui.widget.ApplicationListView;
import com.hmetao.float_quick_application.ui.widget.MyNestedScrollView;
import com.hmetao.float_quick_application.utils.DensityUtil;

import java.util.List;

public class FloatService extends Service implements ApplicationItemView.OnTouchMoveListener {

    private static final String TAG = FloatService.class.getSimpleName();
    private MyNestedScrollView floatRootView;
    private WindowManager wm;
    private List<AppInfo> apps;
    private WindowManager.LayoutParams windowLayoutParams;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListRootView();
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindow() {
        // 获取wm
        wm = WindowManagerHelper.instance.getWindowManager(this);
        windowLayoutParams = WindowManagerHelper.instance.
                buildWindowLayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                        DensityUtil.dip2px(this, 54 * 5));
        wm.addView(floatRootView, windowLayoutParams);
    }

    private MyNestedScrollView buildApplicationListRootView() {
        MyNestedScrollView nestedScrollView = new MyNestedScrollView(this);
        apps = getAppInfo(getBaseContext());
        ApplicationListView applicationListView = new ApplicationListView(this, apps, this);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        // 设置根root的长宽
        nestedScrollView.addView(applicationListView);

//        return applicationListView;
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


    @Override
    public void onTouchMove(float dx, float dy) {
        windowLayoutParams.x += (int) dx;
        windowLayoutParams.y += (int) dy;
        floatRootView.setScrollingEnabled(false);
        wm.updateViewLayout(floatRootView, windowLayoutParams);
    }

    @Override
    public void onTouchMoveStop() {
        floatRootView.setScrollingEnabled(true);
    }
}
