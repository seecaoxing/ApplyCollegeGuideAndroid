package com.xyz.core.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.xyz.core.Core;

/**
 * Created by heq on 17/9/14.
 */

public class AppUtils {

    public static String getAppVersion() {
        return getAppVersion(Core.context().getPackageName());
    }

    /**
     * 根据packageName包名的应用获取应用版本名称,如未安装返回null
     *
     * @param packageName
     * @return
     */
    public static String getAppVersion(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageInfo info = Core.context().getPackageManager().getPackageInfo(packageName, 0);
            if (info != null) {
                return info.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    /**
     * 根据packageName包名的应用获取应用信息,如未安装返回null
     *
     * @param packageName
     * @return
     */
    public static PackageInfo getAppInfo(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            return Core.context().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 根据packageName包名的应用获取应用版本名称,如未安装返回0
     *
     * @param packageName
     * @return
     */
    public static int getAppVersionCode(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return 0;
        }
        try {
            PackageInfo info = Core.context().getPackageManager().getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

}
