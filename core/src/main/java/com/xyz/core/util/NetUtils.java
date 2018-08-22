package com.xyz.core.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.xyz.core.Core;

/**
 * Created by taiwei on 2017/9/7.
 */

public class NetUtils {

    /**
     * 是否使用了代理
     */
    public static boolean withProxy() {
        String proxyHost = System.getProperty("http.proxyHost");
        String port = System.getProperty("http.proxyPort");
        return DataUtils.valid(proxyHost) && DataUtils.getInt(port) > 0;
    }

    /**
     * 检查当前网络是否可用
     */
    public static boolean checkNetwork() {
        boolean flag = false;
        try {
            ConnectivityManager cwjManager = (ConnectivityManager) Core.context()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cwjManager.getActiveNetworkInfo() != null)
                flag = cwjManager.getActiveNetworkInfo().isConnected();//.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
