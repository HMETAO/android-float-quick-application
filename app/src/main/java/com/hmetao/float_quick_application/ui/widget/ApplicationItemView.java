package com.hmetao.float_quick_application.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hmetao.float_quick_application.R;
import com.hmetao.float_quick_application.domain.AppInfo;


public class ApplicationItemView extends LinearLayout {
    private static final String TAG = ApplicationItemView.class.getSimpleName();
    private final Context context;
    public ImageView imageView;
    private static final int TOUCH_MAX = 50;
    private float mLastMotionX;
    private float mLastMotionY;
    private final Handler handler = new Handler(Looper.myLooper());

    private final Runnable r = () -> needMove = true;

    boolean needMove = false;

    private OnTouchMoveListener onTouchMoveListener;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 抬起时,移除已有Runnable回调,抬起就算长按了(不需要考虑用户是否长按了超过预设的时间)
                handler.removeCallbacks(r);
                if (!needMove) {
                    // 打开app
                    AppInfo app = (AppInfo) getTag();
                    startApp(app);
                } else {
                    if (onTouchMoveListener != null)
                        onTouchMoveListener.onTouchMoveStop();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!needMove || Math.abs(mLastMotionX - x) > TOUCH_MAX || Math.abs(mLastMotionY - y) > TOUCH_MAX) {
                    // 移动误差阈值
                    // xy方向判断
                    // 移动超过阈值，则表示移动了,就不是长按,移除 已有的Runnable回调
                    handler.removeCallbacks(r);
                }
                if (needMove) {
                    // 发步移动事件
                    float dx = event.getRawX() - mLastMotionX;
                    float dy = event.getRawY() - mLastMotionY;
                    mLastMotionX = event.getRawX();
                    mLastMotionY = event.getRawY();
                    Log.d(TAG, "  dx: " + dx + ", dy: " + dy);
                    if (onTouchMoveListener != null) {
                        onTouchMoveListener.onTouchMove(dx, dy);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                // 每次按下重新计时
                // 按下前,先移除 已有的Runnable回调,防止用户多次单击导致多次回调长按事件的bug
                handler.removeCallbacks(r);
                mLastMotionX = x;
                mLastMotionY = y;
                needMove = false;
                // 按下时,开始计时
                handler.postDelayed(r, 100);
                break;
        }
        return true;
    }

    private void startApp(AppInfo app) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(app.packageName);
        context.startActivity(intent);
    }

    public interface OnTouchMoveListener {
        void onTouchMove(float dx, float dy);

        void onTouchMoveStop();
    }

    public void setOnTouchMoveListener(OnTouchMoveListener onTouchMoveListener) {
        this.onTouchMoveListener = onTouchMoveListener;
    }
}
