package com.hmetao.float_quick_application.help;

import android.content.Context;
import android.view.WindowManager;

public class WindowManagerHelper {
    public static final WindowManagerHelper instance = new WindowManagerHelper();

    private WindowManagerHelper() {
    }

    public WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }

}
