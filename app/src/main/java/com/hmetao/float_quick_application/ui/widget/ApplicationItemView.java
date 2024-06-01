package com.hmetao.float_quick_application.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hmetao.float_quick_application.R;


public class ApplicationItemView extends LinearLayout {
    private static final String TAG = ApplicationItemView.class.getSimpleName();
    private final Context context;
    public ImageView imageView;

    public ApplicationItemView(Context context) {
        this(context, null);
    }

    public ApplicationItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ApplicationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_application, this, false);
        imageView = root.findViewById(R.id.applicationIcon);
        addView(root);
    }


}
