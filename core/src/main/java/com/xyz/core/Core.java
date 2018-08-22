package com.xyz.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by taiwei on 2017/9/7.
 */

public class Core {

    public static final String DEFAULT_MODULE_NAME = "com.xyz.core";

    @SuppressLint("StaticFieldLeak")
    private static CoreControl sCoreControl;

    public static CoreControl install(Application application) {
        if (sCoreControl == null) {
            sCoreControl = new CoreControl(application);
        }
        return config();
    }

    public static CoreControl config() {
        ensureInstall();
        return sCoreControl;
    }

    public static Context context() {
        ensureInstall();
        return sCoreControl.getContext();
    }

    private static void ensureInstall() {
        if (sCoreControl == null) {
            throw new NullPointerException("请先调用install方法加载application");
        }
    }
}
