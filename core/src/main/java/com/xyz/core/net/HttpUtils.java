package com.xyz.core.net;

import android.text.TextUtils;

import com.xyz.core.util.AppUtils;
import com.xyz.core.util.DeviceUtils;

public class HttpUtils {

    public static final String[] USER_AGENT_ARR = new String[]{"NewsApp/", " Android/", " (", "/", ")"};
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_ADD_TO_QUEUE = "Add-To-Queue-Millis";
    public static final String HEADER_REQUEST_TRACE_ID = "X-NR-Trace-Id";//请求trace_id
    private static String sUserAgent;

    /**
     * 使用StringBuilder提高效率
     * NewsApp/{客户端版本号} {操作系统}/{系统版本} ({设备名称}/{设备信息})
     *
     * @return
     */
    public static String getUserAgent() {
        if (!TextUtils.isEmpty(sUserAgent)) {
            return sUserAgent;
        }
        StringBuilder userAgent = new StringBuilder(USER_AGENT_ARR[0]);
        userAgent.append(AppUtils.getAppVersion())
                .append(USER_AGENT_ARR[1])
                .append(DeviceUtils.getBuildVersionRelease())
                .append(USER_AGENT_ARR[2])
                .append(DeviceUtils.getBrand())
                .append(USER_AGENT_ARR[3])
                .append(DeviceUtils.getModel())
                .append(USER_AGENT_ARR[4]);
        sUserAgent = userAgent.toString();
        return sUserAgent;
    }

}
