package com.guide.applycollegeguide.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by md on 2018/4/28.
 */
public class TransferUtil {
    private static final String FORMAT_DATE = "yyyy.MM.dd";
    private static final String FORMAT_SIZE = "%.1f";

    public static String size(String byteLength) {

        if (TextUtils.isEmpty(byteLength)) {
            return null;
        }

        long length = 0;
        try {
            length = Long.valueOf(byteLength);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // 小于0.1k，直接显示原始数字
        if (length < ((1 << 10) / 10)) {
            return byteLength;
        }

        // 小于0.1m，显示k单位
        if (length < ((1 << 20) / 10)) {
            return String.format(FORMAT_SIZE, length * 1f / (1 << 10)) + "K";
        }

        return String.format(FORMAT_SIZE, length * 1f / (1 << 20)) + "M";
    }

    public static String date(String timestamp) {
        if (TextUtils.isEmpty(timestamp)) {
            return null;
        }
        Date date = new Date();
        date.setTime(Long.valueOf(timestamp) * 1000L);
        return new SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(date);
    }
}
