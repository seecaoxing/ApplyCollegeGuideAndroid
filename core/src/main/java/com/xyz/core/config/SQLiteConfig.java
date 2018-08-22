package com.xyz.core.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.xyz.core.config.ConfigTable.ConfigColumns;

import java.util.HashMap;
import java.util.Map;


public class SQLiteConfig implements IConfig {


    static final String[] PROJECT_CONFIG = new String[]
            {
                    ConfigColumns.C_NAME,
                    ConfigColumns.C_VALUE,
                    ConfigColumns.C_TYPE,

            };

    static final int COLUMNS_NAME = 0;
    static final int COLUMNS_VALUE = 1;
    static final int COLUMNS_TYPE = 2;


    private Context mContext;

    public SQLiteConfig(Context context) {
        mContext = context;
    }


    public void setValue(String groupName, String name, String value) {

        ContentValues cv = new ContentValues();
        cv.put(ConfigColumns.C_GROUP, groupName);
        cv.put(ConfigColumns.C_NAME, name);
        cv.put(ConfigColumns.C_VALUE, value);
        mContext.getContentResolver().insert(ConfigColumns.CONTENT_URI, cv);
    }


    @Override
    public void removeGroup(String group) {


        String where = ConfigColumns.C_GROUP + " = '" + group + "'";

        mContext.getContentResolver().delete(ConfigColumns.CONTENT_URI, where, null);
    }

    @Override
    public boolean isEmpty(String group) {
        String where = ConfigColumns.C_GROUP + " = '" + group + "'";

        boolean ret = false;
        Cursor cr = mContext.getContentResolver().query(ConfigColumns.CONTENT_URI, PROJECT_CONFIG, where, null, null);
        if (cr != null && cr.moveToFirst())
            ret = true;

        if (cr != null)
            cr.close();

        return ret;
    }

    @Override
    public boolean getValue(String group, String key, boolean defaultValue) {

        String value = getValue(group, key, String.valueOf(defaultValue));
        if (TextUtils.isEmpty(value))
            return defaultValue;

        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @Override
    public int getValue(String group, String key, int defaultValue) {

        String value = getValue(group, key, String.valueOf(defaultValue));
        if (TextUtils.isEmpty(value))
            return defaultValue;

        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @Override
    public long getValue(String group, String key, long defaultValue) {

        String value = getValue(group, key, String.valueOf(defaultValue));
        if (TextUtils.isEmpty(value))
            return defaultValue;

        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @Override
    public void setValue(String group, String key, boolean defaultValue) {

        setValue(group, key, String.valueOf(defaultValue));
    }

    @Override
    public void setValue(String group, String key, int defaultValue) {
        setValue(group, key, String.valueOf(defaultValue));

    }

    @Override
    public float getValue(String group, String key, float defaultValue) {

        String value = getValue(group, key, String.valueOf(defaultValue));
        if (TextUtils.isEmpty(value))
            return defaultValue;

        try {
            return Float.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @Override
    public void setValue(String group, String key, float defaultValue) {
        setValue(group, key, String.valueOf(defaultValue));

    }

    @Override
    public void setValue(String group, String key, long defaultValue) {
        setValue(group, key, String.valueOf(defaultValue));

    }

    @Override
    @SuppressWarnings("uncheck")
    public Map<String, Object> getAll(String group) {

        Map<String, Object> result = new HashMap<String, Object>();
        String where = ConfigColumns.C_GROUP + " = '" + group + "'";

        Cursor cr = mContext.getContentResolver().query(ConfigColumns.CONTENT_URI, PROJECT_CONFIG, where, null, null);
        if (cr != null && cr.moveToFirst()) {
            do {
                result.put(cr.getString(COLUMNS_NAME), cr.getString(COLUMNS_VALUE));
            } while (cr.moveToNext());
        }
        if (cr != null)
            cr.close();
        return result;

    }

    @Override
    public String getValue(String group, String name, String defaultValue) {
        String ret = defaultValue;
        String where = ConfigColumns.C_GROUP + " = '" + group + "' AND " + ConfigColumns.C_NAME + " = '" + name + "'";

        Cursor cursor = mContext.getContentResolver().query(ConfigColumns.CONTENT_URI, PROJECT_CONFIG, where, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ret = cursor.getString(COLUMNS_VALUE);
        }

        if (cursor != null)
            cursor.close();

        return ret;
    }

    @Override
    public void unsetValue(String group, String name) {

        String where = ConfigColumns.C_GROUP + " = '" + group + "' AND " + ConfigColumns.C_NAME + " = '" + name + "'";

        mContext.getContentResolver().delete(ConfigColumns.CONTENT_URI, where, null);


    }
}
