package com.xyz.core.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xyz.core.Core;

/**
 * Created by taiwei on 2017/9/7.
 */

public class DeviceUtils {

    /**
     * 获取IMSI(15位,国际移动用户识别码), 结构为 MCC(国家代号,3位) + MNC(移动网号码, 2位) + MSIN(移动用户识别码)
     * 注: 需要注册<android:name="android.permission.READ_PHONE_STATE"/>权限
     */
    public static String getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) Core.context().getSystemService(Context.TELEPHONY_SERVICE);
            String operator = tm.getSubscriberId();
            if (!TextUtils.isEmpty(operator)) {
                return operator;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取设备名称.
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取操作系统类型.
     */
    public static String getBuildOS() {
        return Build.DISPLAY;
    }

    /**
     * 获取ROM制造商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备品牌.
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备SDK版本号.
     */
    public static int getBuildVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备系统版本号.
     */
    public static String getBuildVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    public static int getOSVersion() {
        return Build.VERSION.SDK_INT;
    }
}
