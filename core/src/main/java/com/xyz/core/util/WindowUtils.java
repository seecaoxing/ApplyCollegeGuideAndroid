package com.xyz.core.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by taiwei on 2017/11/24.
 */

public class WindowUtils {

    public static boolean isScreenLandscape(Context context) {
        if (context != null && context.getResources() != null) {
            Configuration configuration = context.getResources().getConfiguration();
            return configuration != null &&
                    configuration.orientation == Configuration.ORIENTATION_LANDSCAPE;
        }
        return false;
    }
}
