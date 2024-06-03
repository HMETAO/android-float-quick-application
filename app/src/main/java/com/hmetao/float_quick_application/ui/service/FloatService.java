package com.hmetao.float_quick_application.ui.service;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hmetao.float_quick_application.domain.AppInfo;
import com.hmetao.float_quick_application.help.WindowManagerHelper;
import com.hmetao.float_quick_application.ui.widget.ApplicationItemView;
import com.hmetao.float_quick_application.ui.widget.ApplicationListView;
import com.hmetao.float_quick_application.ui.widget.LineView;
import com.hmetao.float_quick_application.ui.widget.MyNestedScrollView;
import com.hmetao.float_quick_application.utils.DensityUtil;

import java.util.List;
import java.util.stream.Collectors;

public class FloatService extends Service implements ApplicationItemView.OnTouchMoveListener, LineView.OnChangeViewListener {

    private static final String TAG = FloatService.class.getSimpleName();

    // windowManagerHelper
    WindowManagerHelper helper = WindowManagerHelper.instance;

    // 根root
    private View floatRootView;

    // 悬浮框wm
    private WindowManager wm;

    // 悬浮框布局参数
    private WindowManager.LayoutParams params;

    // 屏幕宽
    private int screenWidth;

    // 屏幕高
    private int screenHeight;

    private boolean isMinimized = false;

    // root下的scroll view
    private MyNestedScrollView scrollView;

    // 收纳view
    private View lineView;

    // 当个的宽
    private int itemWidth;

    //当个的高
    private int itemHeight;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListRootView();
        // 初始化基本参数
        initParams();
        // 初始化window
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initParams() {
        itemWidth = DensityUtil.dip2px(this, 50);
        itemHeight = DensityUtil.dip2px(this, 54);
        // 获取屏幕的宽高
        screenWidth = helper.getScreenWidth(this);
        screenHeight = helper.getScreenHeight(this);
        // 获取wm
        wm = helper.getWindowManager(this);
    }


    private void initWindow() {
        // 构建布局参数 默认高为五个item
        params = helper.buildWindowLayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, itemHeight * 5,
                screenWidth - itemWidth, screenHeight / 5);
        // 添加到item
        wm.addView(floatRootView, params);
    }

    private View buildApplicationListRootView() {
        LinearLayout root = new LinearLayout(this);

        scrollView = new MyNestedScrollView(this);
        // 获取全部app
        List<AppInfo> apps = getAppInfo(getBaseContext()).stream().filter(appInfo -> !appInfo.packageName.equals(getPackageName())).collect(Collectors.toList());
        // 生成apps view 并注册move回调
        ApplicationListView applicationListView = new ApplicationListView(this, apps, this);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        // 套一层scroll
        scrollView.addView(applicationListView);
        // 添加到root
        root.addView(scrollView);

        // 收纳棍高度
        int lineHeight = itemHeight * 2;
        // 初始化收纳棍
        lineView = new LineView(this, DensityUtil.dip2px(this, 6), lineHeight, this);
        // 添加到root
        root.addView(lineView);

        return root;
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
    public void onTouchMove(float dx, float dy) {
        // 禁止内部的scroll滚动，避免与move冲突
        scrollView.setScrollingEnabled(false);
        // 判断是否在屏幕范围内
        float newX = params.x + dx;
        float newY = params.y + dy;
        Log.d(TAG, "newXY: " + newX + " next  " + newY);
        // 判断是否超过屏幕宽
        if (newX >= 0 && newX <= screenWidth - itemWidth) {
            params.x = (int) newX;
            isMinimized = false;
        } else {
            params.x = newX < 0 ? 0 : screenWidth;
            isMinimized = true; // 横向越界了触发收纳
        }
        // 判断是否超过屏幕高
        if (newY >= 0 && newY <= screenHeight) params.y = (int) newY;
        Log.d(TAG, "onTouchMove: " + params.x + " next  " + params.y);
        // 设置透明虚化背景
        floatRootView.setAlpha(0.5f);
        wm.updateViewLayout(floatRootView, params);
    }


    @Override
    public void onTouchMoveStop() {
        // 开启滚动限制
        scrollView.setScrollingEnabled(true);
        floatRootView.setAlpha(1);
        if (isMinimized) {
            // 收纳
            onChangeVisibleOrGone(scrollView);
        }
    }

    public void onChangeVisibleOrGone(View view) {
        if (view.equals(scrollView)) {
            lineView.setVisibility(ViewGroup.VISIBLE);
            scrollView.setVisibility(ViewGroup.GONE);
        } else {
            lineView.setVisibility(ViewGroup.GONE);
            scrollView.setVisibility(ViewGroup.VISIBLE);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onMaximize(View visibleView, float rawX) {
        if ((int) rawX > screenWidth / 2) {
            params.x = screenWidth - itemWidth;
        }
        onChangeVisibleOrGone(visibleView);
        wm.updateViewLayout(floatRootView, params);
    }
}
