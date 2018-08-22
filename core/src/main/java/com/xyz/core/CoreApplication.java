package com.xyz.core;

import android.app.Application;

import com.xyz.core.glide.GlideCacheUtils;
import com.xyz.core.log.NTLog;

/**
 * Created by jason on 2017/10/20.
 */
public class CoreApplication extends Application {

    private static CoreApplication mInstance;

    public static CoreApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    protected void initCore(CoreConfig config) {
        Core.install(this);
        // 初始化NTLog 初始化在最前面否则父类中的日子无法打印
        NTLog.init(config.isLogDebug(), config.getApplication().getPackageName());

        // 图片模块初始化
        GlideCacheUtils.DiskCacheInfo diskCacheInfo = GlideCacheUtils.ensureDiskCacheFolder();
    }
}
