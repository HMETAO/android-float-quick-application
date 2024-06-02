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

import com.hmetao.float_quick_application.R;
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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListRootView();
        // 初始化window
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindow() {
        // 获取屏幕的宽高
        screenWidth = helper.getScreenWidth(this);
        screenHeight = helper.getScreenHeight(this);
        // 获取wm
        wm = helper.getWindowManager(this);
        // 构建布局参数 默认高为五个item
        params = helper.buildWindowLayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(this, 54 * 5), 0, 0);
        // 添加到item
        wm.addView(floatRootView, params);
    }

    private View buildApplicationListRootView() {
        LinearLayout root = new LinearLayout(this);

        scrollView = new MyNestedScrollView(this);
        // 获取全部app
        List<AppInfo> apps = getAppInfo(getBaseContext());
        // 生成apps view 并注册move回调
        ApplicationListView applicationListView = new ApplicationListView(this, apps, this);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        // 套一层scroll
        scrollView.addView(applicationListView);
        // 添加到root
        root.addView(scrollView);

        // 构建收纳view
        lineView = new View(this);
        // 设置虚化
        lineView.setAlpha(0.7f);
        // 背景色
        lineView.setBackgroundColor(getColor(R.color.black));
        // 隐藏
        lineView.setVisibility(ViewGroup.GONE);
        // 添加到root todo
        root.addView(lineView, new LinearLayout.LayoutParams(10, 20));


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
        boolean check = false;
        // 判断是否超过屏幕宽
        if (newX >= 0 && newX <= screenWidth)
            params.x = (int) newX;
        else check = true; // 横向越界了触发收纳
        // 判断是否超过屏幕高
        if (newY >= 0 && newY <= screenHeight)
            params.y = (int) newY;
        Log.d(TAG, "onTouchMove: " + params.x + " next  " + params.y);
        if (check) {
            // 收纳
            animateToLine();
        }
        // 设置透明虚化背景
        floatRootView.setAlpha(0.5f);
        wm.updateViewLayout(floatRootView, params);
    }

    private void animateToLine() {
        lineView.setVisibility(ViewGroup.VISIBLE);
        scrollView.setVisibility(ViewGroup.GONE);
    }

    @Override
    public void onTouchMoveStop() {
        // 开启滚动限制
        scrollView.setScrollingEnabled(true);
        floatRootView.setAlpha(1);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
