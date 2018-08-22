package com.xyz.core.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;

import com.xyz.core.CoreApplication;
import com.xyz.core.log.NTLog;

import java.io.File;
import java.net.URLEncoder;

import it.sauronsoftware.base64.Base64;

/**
 * 系统工具
 * <p>
 * Created by jason on 2017/10/23.
 */
public class SystemUtils {

    private static final int CSI_DEVICE_ID = 1;                                 //设备id
    private static final int CSI_CHANNEL_ID = CSI_DEVICE_ID << 1;               //渠道号
    private static final int CSI_USER_AGENT = CSI_CHANNEL_ID << 1;              //User-Agent
    private static final int CSI_DEVICES_IMEI = CSI_USER_AGENT << 1;            //获取设备imei
    private static final int CSI_DEVICES_MAC = CSI_DEVICES_IMEI << 1;           //设备mac地址
    private static final int CSI_APP_VERSION_NAME = CSI_DEVICES_MAC << 1;       //versionName
    private static final int CSI_APP_VERSION_CODE = CSI_APP_VERSION_NAME << 1;  //versionCode
    private static final int CSI_SCREEN_WIDTH = CSI_APP_VERSION_CODE << 1;      //设备屏幕宽
    private static final int CSI_SCREEN_HEIGHT = CSI_SCREEN_WIDTH << 1;         //设备屏幕高
    private static final int CSI_STATUS_BAR_HEIGHT = CSI_SCREEN_HEIGHT << 1;    //状态栏高度

    /**
     * 添加缓存信息需修改{@link #commonInfoArray} 默认长度
     */
    private static SparseArray<Object> commonInfoArray = new SparseArray<>(12);


    public static String getUserAgent() {
        String userAgent = "";
        Object cacheValue = commonInfoArray.get(CSI_USER_AGENT);
        if (cacheValue != null && cacheValue instanceof String) {
            userAgent = ((String) cacheValue);
        } else {
            try {
                userAgent = System.getProperty("http.agent");
                StringBuffer sb = new StringBuffer();
                for (int i = 0, length = userAgent.length(); i < length; i++) {
                    char c = userAgent.charAt(i);
                    if (c <= '\u001f' || c >= '\u007f') {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
                userAgent = sb.toString();
                commonInfoArray.put(CSI_USER_AGENT, userAgent);
            } catch (Exception e) {
                NTLog.e("SystemUtils", "getUserAgent:" + e.toString());
            }
        }
        return userAgent;
    }

    /**
     * 生成一个请求唯一的traceId
     *
     * @param requestIdentity 一个网络请求的唯一标识，一般用request.hashcode
     * @return
     */
    public static String getTraceId(String requestIdentity) {
        StringBuilder traceId = new StringBuilder();
        traceId.append(System.currentTimeMillis());
        traceId.append("_")
                .append(requestIdentity)
                .append("_")
                .append(SystemUtils.getDeviceId());
        return traceId.toString();
    }

    /**
     * 获取设备ID.
     *
     * @return
     */
    public static String getDeviceId() {
        String deviceID = "";
        Object cacheValue = commonInfoArray.get(CSI_DEVICE_ID);
        if (cacheValue != null && cacheValue instanceof String) {
            deviceID = ((String) cacheValue);
        } else {
            deviceID = getDeviceId(CoreApplication.getInstance());
            commonInfoArray.put(CSI_DEVICE_ID, deviceID);
        }
        return deviceID;
    }

    /**
     * 获取渠道号
     *
     * @return
     */
    public static String getChannelId(String defaultChannel) {
        String channelId = "";
        Object cacheValue = commonInfoArray.get(CSI_CHANNEL_ID);
        if (cacheValue != null && cacheValue instanceof String) {
            channelId = ((String) cacheValue);
        } else {
            channelId = ChannelUtils.getChannel(CoreApplication.getInstance(), defaultChannel);
            System.out.println("SystemUtils..channelId==>>" + channelId);
            commonInfoArray.put(CSI_CHANNEL_ID, channelId);
        }
        return channelId;
    }

    /**
     * 获取设备imei
     *
     * @return
     */
    public static String getIMEI() {
        String imei = "";
        Object cacheValue = commonInfoArray.get(CSI_DEVICES_IMEI);
        if (cacheValue != null && cacheValue instanceof String) {
            imei = (String) cacheValue;
        } else {
            // 没有获取READ_PHONE_STATE权限，此方法会抛出异常
            try {
                TelephonyManager tm = (TelephonyManager) CoreApplication.getInstance()
                        .getSystemService(Context.TELEPHONY_SERVICE);
                imei = tm.getDeviceId();
                commonInfoArray.put(CSI_DEVICES_IMEI, imei);
            } catch (Exception e) {
                NTLog.e("SystemUtils", "getIMEI:" + e.toString());
            }
        }
        return imei;
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMac() {
        String macAddress = "";
        Object cacheValue = commonInfoArray.get(CSI_DEVICES_MAC);
        if (cacheValue != null && cacheValue instanceof String) {
            macAddress = ((String) cacheValue);
        } else {
            try {
                WifiManager wifi = (WifiManager) CoreApplication.getInstance()
                        .getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                String mac = info.getMacAddress();
                macAddress = TextUtils.isEmpty(mac) ? "" : mac;
                commonInfoArray.put(CSI_DEVICES_MAC, macAddress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return macAddress;
    }

    /**
     * 是否是模拟器
     *
     * @return
     */
    public static boolean isEmulator() {
        String imei = getIMEI();
        if ("000000000000000".equals(imei)) {
            return true;
        }
        return (Build.MODEL.equalsIgnoreCase("sdk"))
                || (Build.MODEL.equalsIgnoreCase("google_sdk"))
                || Build.BRAND.equalsIgnoreCase("generic");
    }

    public static String getAppVersion() {
        String appVersion = "";
        Object cacheValue = commonInfoArray.get(CSI_APP_VERSION_NAME);
        if (cacheValue != null && cacheValue instanceof String) {
            appVersion = ((String) cacheValue);
        } else {
            appVersion = getAppVersion(getAppPackageName());
            commonInfoArray.put(CSI_APP_VERSION_NAME, appVersion);
        }
        return appVersion;
    }

    public static String getAppVersionCode() {
        String appVersionCode = "";
        Object cacheValue = commonInfoArray.get(CSI_APP_VERSION_CODE);
        if (cacheValue != null && cacheValue instanceof String) {
            appVersionCode = ((String) cacheValue);
        } else {
            appVersionCode = getAppVersionCode(getAppPackageName());
            commonInfoArray.put(CSI_APP_VERSION_CODE, appVersionCode);
        }
        return appVersionCode;
    }

    public static String getAppPackageName() {
        String result;
        try {
            result = CoreApplication.getInstance().getPackageName();
        } catch (Exception e) {
            //某些手机获取报名抛出了异常
            result = "com.xyz";
        }
        return result;
    }


    public static String getAppVersion(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageInfo info = CoreApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
            if (info != null) {
                return info.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }


    public static String getAppVersionCode(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageInfo info = CoreApplication.getInstance().getPackageManager().getPackageInfo(packageName, 0);
            if (info != null) {
                return String.valueOf(info.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static String getOperatorCode() {
        TelephonyManager telephonyManager = (TelephonyManager) CoreApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimOperator();
    }

    /**
     * 获取高度，默认读缓存
     *
     * @return
     */
    public static int getScreenHeight() {
        return getScreenHeight(false);
    }

    /**
     * 获取宽度，默认读缓存
     *
     * @return
     */
    public static int getScreenWidth() {
        return getScreenWidth(false);
    }

    /**
     * 获取手机分辨率长度大小
     *
     * @return
     */
    public static int getScreenHeight(boolean skipCache) {
        int screenHeight = -1;
        try {
            if (skipCache) {
                screenHeight = CoreApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
            } else {
                Object cacheValue = commonInfoArray.get(CSI_SCREEN_HEIGHT);
                if (cacheValue != null && cacheValue instanceof Integer) {
                    screenHeight = ((Integer) cacheValue);
                } else {
                    screenHeight = CoreApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
                    if (screenHeight >= 0) {
                        commonInfoArray.put(CSI_SCREEN_HEIGHT, screenHeight);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }

    /**
     * 获取手机分辨率宽度大小
     *
     * @return
     */
    public static int getScreenWidth(boolean skipCache) {
        int screenWith = -1;
        try {
            if (skipCache) {
                screenWith = CoreApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
            } else {
                Object cacheValue = commonInfoArray.get(CSI_SCREEN_WIDTH);
                if (cacheValue != null && cacheValue instanceof Integer) {
                    screenWith = ((Integer) cacheValue);
                } else {
                    screenWith = CoreApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
                    if (screenWith >= 0) {
                        commonInfoArray.put(CSI_SCREEN_WIDTH, screenWith);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenWith;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        Object cacheValue = commonInfoArray.get(CSI_STATUS_BAR_HEIGHT);
        if (cacheValue != null && cacheValue instanceof Integer) {
            statusBarHeight = ((Integer) cacheValue);
        } else {
            Class<?> c;
            Object obj;
            java.lang.reflect.Field field;
            int x;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = CoreApplication.getInstance().getResources().getDimensionPixelSize(x);
                return statusBarHeight;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statusBarHeight >= 0) {
                commonInfoArray.put(CSI_STATUS_BAR_HEIGHT, statusBarHeight);
            }
        }
        return statusBarHeight;
    }

    public static long getRomAvailableSize() {
        return getAvailableSize(Environment.getDataDirectory());
    }

    public static long getSDAvailableSize() {
        return getAvailableSize(Environment.getExternalStorageDirectory());
    }

    public static long getAvailableSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        String path = file.getAbsolutePath();
        StatFs stat = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return stat.getAvailableBytes();
        } else {
            return stat.getBlockSize() * ((long) stat.getAvailableBlocks());
        }
    }

    public static float dp2px(float dp) {
        final float scale = CoreApplication.getInstance().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(float sp) {
        final float scale = CoreApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static void insertGallery(Context context, File file, String fileName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static void insertGallery(Context context, String fileName) {
        insertGallery(context, new File(fileName), fileName);
    }

    public static String getHeaderNetType(Context context) {
        if (context == null)
            return "unknown";

        if (NetWorkUtils.isWifi(context)) {
            NetWorkUtils.WiFiBean bean = NetWorkUtils.getWifiInfo(context);
            if (bean != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("WiFi")
                        .append("#")
                        .append(Base64.encode(bean.getSsid())) //防止ssid有#，因此需要encode
                        .append("#")
                        .append(bean.getMacAddress());
                return sb.toString();
            }
        }
        return NetWorkUtils.getNetType(context);
    }

    static String getDeviceId(Context context) {
        String id;
        if (Build.VERSION.SDK_INT < 23) {
            // 6.0以下设备
            id = getDeviceIdBelowM(context);
        } else {
            // 6.0以上设备
            id = getDeviceIdUpM(context);
        }
        return id;
    }

    /**
     * 在6.0以下系统获取设备ID
     */
    private static synchronized String getDeviceIdBelowM(Context context) {
        // 先取imei
        String id = getIMEI(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }

        // 再取mac地址
        id = getMac(context);
        if (!TextUtils.isEmpty(id)) {
            return id;
        }

        // 如果取不到，取android id
        if (!isEmulator(context)) {
            id = getAndroidId(context);
        }

        // 都取不到, 则取默认imei
        if (TextUtils.isEmpty(id)) {
            id = DEFAULT_IMEI;
        }
        return id;
    }

    /**
     * 在6.0以上系统获取设备ID.
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    private static synchronized String getDeviceIdUpM(Context context) {
        String sep = "\t";
        String serial = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            serial = Build.SERIAL;
        }
        if (serial == null) {
            serial = "";
        }
        serial = serial.trim();

        if (serial.length() > 20) {
            serial = serial.substring(0, 20);
        }
        String deviceIdPrefix = sep + sep;
        String deviceId = android.util.Base64.encodeToString((deviceIdPrefix + Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID) + sep + serial).getBytes(), android.util.Base64.NO_WRAP);
        deviceId = URLEncoder.encode(deviceId);
        return deviceId;
    }

    /**
     * 是否是模拟器
     */
    static boolean isEmulator(Context context) {
        String imei = getIMEI(context);
        if (DEFAULT_IMEI.equals(imei)) {
            return true;
        }
        return (Build.MODEL.equalsIgnoreCase("sdk")) || (Build.MODEL.equalsIgnoreCase("google_sdk")) || Build.BRAND.equalsIgnoreCase("generic");
    }

    /**
     * 获取imei, 优先获取暂存的值
     * 注: 需要注册<android:name="android.permission.READ_PHONE_STATE"/>权限
     */
    static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取mac地址, 优先获取暂存的值
     */
    static String getMac(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取androidid, 优先获取暂存的值
     */
    static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    static final String DEFAULT_IMEI = "000000000000000";
}