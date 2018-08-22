package com.xyz.core.config;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jason on 16/6/1.
 */
public class ConfigTable {


    public static final String AUTHORITY = "com.netease.framework.core.config";


    interface Config {
        static final String C_GROUP = "group_name";
        static final String C_NAME = "name";
        static final String C_VALUE = "value";
        static final String C_TYPE = "type";

    }

    public static final class ConfigColumns implements Config, BaseColumns {
        public static final String TABLE_NAME = "config";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.netease." + TABLE_NAME;
        public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.netease." + TABLE_NAME;


        static final int TYPE_INT = 0;
        static final int TYPE_BOOLEAN = 1;
        static final int TYPE_STRING = 2;
    }

}
