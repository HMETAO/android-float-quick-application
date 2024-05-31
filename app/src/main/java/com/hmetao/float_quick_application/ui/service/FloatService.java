package com.hmetao.float_quick_application.ui.service;

import static com.hmetao.float_quick_application.utils.AppUtil.getAppInfo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hmetao.float_quick_application.help.WindowManagerHelper;
import com.hmetao.float_quick_application.ui.compose.ApplicationListView;

public class FloatService extends Service {

    private ApplicationListView floatRootView;
    private WindowManager wm;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        floatRootView = buildApplicationListView();
        initWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    private void initWindow() {
        // 获取wm
        wm = WindowManagerHelper.instance.getWindowManager(this);
        // 创建布局参数
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        //这里需要进行不同的设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置透明度
        params.alpha = 1.0f;
        //设置内部视图对齐方式
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        //是指定窗口的像素格式为 RGBA_8888。
        //使用 RGBA_8888 像素格式的窗口可以在保持高质量图像的同时实现透明度效果。
        params.format = PixelFormat.RGBA_8888;
        //设置窗口的宽高,这里为自动
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //这段非常重要，是后续是否穿透点击的关键
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  //表示悬浮窗口不需要获取焦点，这样用户点击悬浮窗口以外的区域，就不需要关闭悬浮窗口。
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;    //表示悬浮窗口不会阻塞事件传递，即用户点击悬浮窗口以外的区域时，事件会传递给后面的窗口处理。
        //这里的引入布局文件的方式，也可以动态添加控件
        wm.addView(floatRootView, params);
    }

    private ApplicationListView buildApplicationListView() {
        ApplicationListView applicationListView = new ApplicationListView(this, getAppInfo(getBaseContext()));
        // 设置布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        applicationListView.setLayoutParams(params);
        // 设置方向
        applicationListView.setOrientation(LinearLayout.VERTICAL);
        return applicationListView;
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