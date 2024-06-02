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

    // windowManagerHelper
    WindowManagerHelper helper = WindowManagerHelper.instance;

    // 根root
    private MyNestedScrollView floatRootView;

    // 悬浮框wm
    private WindowManager wm;

    // 悬浮框布局参数
    private WindowManager.LayoutParams windowLayoutParams;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListRootView();
        // 初始化window
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindow() {
        // 获取wm
        wm = helper.getWindowManager(this);
        // 构建布局参数 默认高为五个item
        windowLayoutParams = helper.buildWindowLayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(this, 54 * 5));
        // 添加到item
        wm.addView(floatRootView, windowLayoutParams);
    }

    private MyNestedScrollView buildApplicationListRootView() {
        MyNestedScrollView nestedScrollView = new MyNestedScrollView(this);
        // 获取全部app
        List<AppInfo> apps = getAppInfo(getBaseContext());
        // 生成apps view
        ApplicationListView applicationListView = new ApplicationListView(this, apps, this);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        // 套一层scroll
        nestedScrollView.addView(applicationListView);
        return nestedScrollView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatRootView != null) {
            // 从屏幕上回收悬浮框
            wm.removeView(floatRootView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onTouchMove(float dx, float dy) {
        // 禁止内部的scroll滚动，避免与move冲突
        floatRootView.setScrollingEnabled(false);
        windowLayoutParams.x += (int) dx;
        windowLayoutParams.y += (int) dy;
        wm.updateViewLayout(floatRootView, windowLayoutParams);
    }

    @Override
    public void onTouchMoveStop() {
        // 开启滚动限制
        floatRootView.setScrollingEnabled(true);
    }
}
