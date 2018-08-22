package com.xyz.core.log;

/**
 * Created by heq on 17/9/14.
 */

public interface Logger {
    void v(String tag, String msg);

    void d(String tag, String msg);

    void i(String tag, String msg);

    void w(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, Throwable throwable);

}
