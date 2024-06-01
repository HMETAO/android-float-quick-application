package com.hmetao.float_quick_application.help;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;

public class WindowManagerHelper {
    public static final WindowManagerHelper instance = new WindowManagerHelper();

    private WindowManagerHelper() {
    }

    public WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public WindowManager.LayoutParams buildWindowLayoutParams(int width, int height) {
        // 创建布局参数
        WindowManager.LayoutParams windowLayoutParams = new WindowManager.LayoutParams();
        //这里需要进行不同的版本兼容设置
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            windowLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置透明度
        windowLayoutParams.alpha = 1.0f;
        //设置内部视图对齐方式
        windowLayoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        windowLayoutParams.y = -(height / 4);
        //是指定窗口的像素格式为 RGBA_8888。
        //使用 RGBA_8888 像素格式的窗口可以在保持高质量图像的同时实现透明度效果。
        windowLayoutParams.format = PixelFormat.RGBA_8888;
        //设置窗口的宽高,这里为自动
        windowLayoutParams.width = width;
        windowLayoutParams.height = height;
        //这段非常重要，是后续是否穿透点击的关键
        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  //表示悬浮窗口不需要获取焦点，这样用户点击悬浮窗口以外的区域，就不需要关闭悬浮窗口。
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;    //表示悬浮窗口不会阻塞事件传递，即用户点击悬浮窗口以外的区域时，事件会传递给后面的窗口处理。
        //这里的引入布局文件的方式，也可以动态添加控件
        return windowLayoutParams;
    }

}
