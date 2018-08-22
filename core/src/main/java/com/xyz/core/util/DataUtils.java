package com.xyz.core.util;

import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by taiwei on 2017/9/7.
 */

public class DataUtils {

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static <T> T checkArgLegal(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    public static <T> boolean valid(T data) {
        return data != null;
    }

    public static <T> boolean valid(T... data) {
        return data != null && data.length > 0;
    }

    public static <T> boolean valid(List<T> data) {
        return data != null && !data.isEmpty();
    }

    public static boolean valid(String data) {
        return !TextUtils.isEmpty(data);
    }

    public static <T> boolean contains(List<T> data, T t) {
        return valid(data) && data.contains(t);
    }

    public static int getInt(String str) {
        return getInt(str, 0);
    }

    public static int getInt(String str, int defaultValue) {
        try {
            return valid(str) ? Integer.parseInt(str) : defaultValue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long getLong(String str) {
        return getLong(str, 0);
    }

    public static long getLong(String str, long defaultValue) {
        try {
            return valid(str) ? Long.parseLong(str) : defaultValue;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static <T> List<T> arrayToList(T[] array) {
        if (array == null) {
            return null;
        }
        return Arrays.asList(array);
    }

    public static <T> T[] listToArray(List<T> list, Class<T> clazz) {
        if (list == null) {
            return null;
        }
        return list.toArray((T[]) Array.newInstance(clazz, list.size()));
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> List<T> copyList(List<T> origList, Class<T> clazz) {
        if (origList == null) {
            return null;
        }
        T[] origArray = listToArray(origList, clazz);
        T[] destArray = (T[]) Array.newInstance(clazz, origList.size());
        System.arraycopy(origArray, 0, destArray, 0, origList.size());
        List<T> destList = arrayToList(destArray);
        return destList;
    }

    public static <T> boolean isEqual(T data1, T data2) {
        if (data1 == null && data2 == null) {
            return true;
        }
        if (data1 != null && data1.equals(data2)) {
            return true;
        }
        return false;
    }

    public static boolean isListEqual(List list1, List list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!isEqual(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloatEqual(float f1, float f2) {
        return Math.abs(f1 - f2) < 0.000001f;
    }

    public static <T> T getItemData(List<T> list, int pos) {
        if (!isEmpty(list) && pos >= 0 && pos < list.size()) {
            return list.get(pos);
        }
        return null;
    }

    public static <T> int getListSize(List<T> list) {
        if (valid(list)) {
            return list.size();
        }
        return 0;
    }
}
