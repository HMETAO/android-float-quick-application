package com.hmetao.float_quick_application.ui.compose;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hmetao.float_quick_application.R;
import com.hmetao.float_quick_application.databinding.ItemApplicationBinding;
import com.hmetao.float_quick_application.domain.AppInfo;

import java.util.List;

@SuppressLint("ViewConstructor")
public class ApplicationListView extends LinearLayout {
    private final Context context;

    public ApplicationListView(Context context, List<AppInfo> apps) {
        super(context);
        this.context = context;
        // 渲染app_item到view
        renderItem(apps);
    }

    private void renderItem(List<AppInfo> apps) {
        for (AppInfo app : apps) {
            if (app.appIcon == null) continue;
            // 生成item
            LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_application, this, false);
            ImageView imageView = root.findViewById(R.id.applicationIcon);
            // 设置图片
            imageView.setImageDrawable(app.appIcon);
            // 设置缩放
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            // 添加到主view
            addView(root);
            // 设置点击事件
            root.setOnClickListener(v -> {
                // 跳转应用
                Intent wantStartApp = context.getPackageManager().getLaunchIntentForPackage(app.packageName);
                context.startActivity(wantStartApp);
            });
        }

    }


}
