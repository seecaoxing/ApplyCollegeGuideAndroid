package com.xyz.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by taiwei on 2017/10/19.
 */

public class TimeUtils {

    public static String getFormatDate(long time) {
        if (time < 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }


}
