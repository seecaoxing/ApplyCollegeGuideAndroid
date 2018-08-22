package com.xyz.core.util;

import android.database.Cursor;

import java.io.Closeable;

/**
 * Created by heq on 17/9/13.
 */

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
