package com.xyz.core.log;

import android.util.Log;

/**
 * Created by heq on 17/9/14.
 */

public class SystemLogger implements Logger {
    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        Log.e(tag, Log.getStackTraceString(throwable));
    }

}
