package com.xyz.core.config;

import android.content.Context;

import com.xyz.core.log.NTLog;

import junit.framework.Assert;

import java.util.Map;

/**
 * Created by jason on 16/5/12.
 */
public class ConfigManager {

    // config data type
    public static final int CONFIG_TYPE_FILE = 1;
    public static final int CONFIG_TYPE_DB = 2;
    public static final String CONFIG_GROUP_NAME = "SP_DATA";
    static final String TAG = ConfigManager.class.getName();

    private IConfig mConfig;
    private String mGroup;

    public ConfigManager(Context context) {
        this(context, CONFIG_GROUP_NAME);
    }

    public ConfigManager(Context context, String group) {
        this(context, CONFIG_TYPE_FILE, group);
    }

    public ConfigManager(Context context, int configType, String group) {
        if (CONFIG_TYPE_DB == configType) {
            mConfig = new SQLiteConfig(context);
        } else if (CONFIG_TYPE_FILE == configType) {
            mConfig = new FileConfig(context);
        } else {
            Assert.assertTrue(false);
            NTLog.e(TAG, "error config type  is : " + configType);
        }

        mGroup = group;
    }

    public String getValue(String key, String defaultValue) {
        return mConfig.getValue(mGroup, key, defaultValue);
    }

    public void setValue(String key, String value) {
        mConfig.setValue(mGroup, key, value);
    }

    public void delValue(String key) {
        mConfig.unsetValue(mGroup, key);
    }

    public void removeGroup() {
        mConfig.removeGroup(mGroup);
    }

    public boolean isEmpty() {
        return mConfig.isEmpty(mGroup);
    }

    /*****
     * support  special type
     ******/


    public boolean getValue(String key, boolean defaultValue) {
        return mConfig.getValue(mGroup, key, defaultValue);
    }

    public float getValue(String key, float defaultValue) {
        return mConfig.getValue(mGroup, key, defaultValue);
    }

    public int getValue(String key, int defaultValue) {
        return mConfig.getValue(mGroup, key, defaultValue);
    }

    public long getValue(String key, long defaultValue) {
        return mConfig.getValue(mGroup, key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mConfig.getAll(mGroup);
    }

    public void setValue(String key, boolean value) {
        mConfig.setValue(mGroup, key, value);
    }

    public void setValue(String key, float value) {
        mConfig.setValue(mGroup, key, value);
    }

    public void setValue(String key, int value) {
        mConfig.setValue(mGroup, key, value);
    }

    public void setValue(String key, long value) {
        mConfig.setValue(mGroup, key, value);
    }


}
