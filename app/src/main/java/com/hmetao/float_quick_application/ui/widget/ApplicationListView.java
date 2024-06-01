package com.hmetao.float_quick_application.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hmetao.float_quick_application.domain.AppInfo;

import java.util.List;

@SuppressLint("ViewConstructor")
public class ApplicationListView extends LinearLayout {
    private static final String TAG = ApplicationItemView.class.getSimpleName();
    private final Context context;


    public ApplicationListView(Context context, List<AppInfo> apps, ApplicationItemView.OnTouchMoveListener onTouchMoveListener) {
        super(context);
        this.context = context;
        // 渲染app_item到view
        renderItem(apps, onTouchMoveListener);
    }


    private void renderItem(List<AppInfo> apps, ApplicationItemView.OnTouchMoveListener onTouchMoveListener) {
        for (AppInfo app : apps) {
            if (app.appIcon == null) continue;
            // 生成item
            ApplicationItemView root = new ApplicationItemView(context);
            ImageView imageView = root.imageView;
            // 设置图片
            imageView.setImageDrawable(app.appIcon);
            // 设置缩放
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            root.setTag(app);
            // 添加到主view
            addView(root);
            root.setOnTouchMoveListener(onTouchMoveListener);
        }
    }


}
