package com.xyz.core.config;

import java.util.Map;

//TODO  chanage all config type to string
public interface IConfig {

    String getValue(String group, String key, String defaultValue);

    boolean getValue(String group, String key, boolean defaultValue);

    int getValue(String group, String key, int defaultValue);

    long getValue(String group, String key, long defaultValue);

    float getValue(String group, String key, float defaultValue);

    void setValue(String group, String key, String value);

    void setValue(String group, String key, boolean defaultValue);

    void setValue(String group, String key, int defaultValue);

    void setValue(String group, String key, long defaultValue);

    void setValue(String group, String key, float defaultValue);

    void unsetValue(String group, String key);

    void removeGroup(String group);

    boolean isEmpty(String group);

    Map<String, ?> getAll(String group);
}
