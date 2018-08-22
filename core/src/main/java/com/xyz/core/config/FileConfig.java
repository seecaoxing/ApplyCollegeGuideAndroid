package com.xyz.core.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Map;


// when to save the data in memory by apply?    because commit with IO Operation

/**
 * Created by jason on 16/6/1.
 * when to save the data in memory by apply?    because commit with IO Operation
 */
public class FileConfig implements IConfig {

    Context mContext;

    public FileConfig(Context context) {
        mContext = context;
    }

    @Override
    public float getValue(String group, String key, float defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        try {
            return sp.getFloat(key, defaultValue);
        } catch (ClassCastException e) {
            unsetValue(group, key);
            return defaultValue;
        }
    }

    @Override
    public void setValue(String group, String key, float defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().putFloat(key, defaultValue);
        editor.apply();
    }

    @Override
    public boolean getValue(String group, String key, boolean defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        try {
            return sp.getBoolean(key, defaultValue);
        } catch (ClassCastException e) {
            unsetValue(group, key);
            return defaultValue;
        }

    }

    @Override
    public int getValue(String group, String key, int defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        try {
            return sp.getInt(key, defaultValue);
        } catch (ClassCastException e) {
            unsetValue(group, key);
            return defaultValue;
        }

    }

    @Override
    public long getValue(String group, String key, long defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        try {
            return sp.getLong(key, defaultValue);
        } catch (ClassCastException e) {
            unsetValue(group, key);
            return defaultValue;
        }
    }

    @Override
    public void setValue(String group, String key, boolean defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().putBoolean(key, defaultValue);
        editor.apply();
    }

    @Override
    public void setValue(String group, String key, int defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().putInt(key, defaultValue);
        editor.apply();
    }

    @Override
    public void setValue(String group, String key, long defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().putLong(key, defaultValue);
        editor.apply();
    }

    @Override
    public String getValue(String group, String name, String defaultValue) {
        SharedPreferences sp = getSharedPreference(group);
        try {
            return sp.getString(name, defaultValue);
        } catch (ClassCastException e) {
            unsetValue(group, name);
            return defaultValue;
        }
    }

    @Override
    public boolean isEmpty(String group) {
        SharedPreferences sp = getSharedPreference(group);
        if (sp.getAll() == null || sp.getAll().isEmpty())
            return true;
        return false;
    }

    @Override
    public void removeGroup(String name) {
        SharedPreferences sp = getSharedPreference(name);
        Editor editor = sp.edit().clear();
        editor.apply();
    }

    @Override
    public void setValue(String group, String name, String value) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().putString(name, value);
        editor.apply();
    }

    @Override
    public void unsetValue(String group, String name) {
        SharedPreferences sp = getSharedPreference(group);
        Editor editor = sp.edit().remove(name);
        editor.apply();

    }


    @Override
    public Map<String, ?> getAll(String group) {
        SharedPreferences sp = getSharedPreference(group);
        return sp.getAll();
    }

    //support default sharedpreference
    private SharedPreferences getSharedPreference(String group) {
        SharedPreferences sp = null;
        if (TextUtils.isEmpty(group)) {
            sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        } else {
            sp = mContext.getSharedPreferences(group,
                    Context.MODE_PRIVATE);
        }
        return sp;
    }

}
