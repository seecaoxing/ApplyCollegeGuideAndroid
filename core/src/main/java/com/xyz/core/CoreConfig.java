package com.xyz.core;

import android.app.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiasong on 17/5/23.
 */
public class CoreConfig {

    private final Application application;
    private final boolean isDnsEnable;
    private final boolean isLogDebug;
    private final int netThreadPoolSize;
    private final List<String> DNSSupportHosts;
    private final boolean isDnsDebugEnabled;
    private final Map<String, List<String>> debugDnsMap;

    private CoreConfig(Builder builder) {
        application = builder.mApplication;
        isDnsEnable = builder.mIsDnsEnable;
        isLogDebug = builder.mIsLogDebug;
        netThreadPoolSize = builder.mNetThreadPoolSize;
        DNSSupportHosts = builder.mDNSSupportHosts;
        isDnsDebugEnabled = builder.mIsDnsDebugEnabled;
        debugDnsMap = builder.mDebugDnsMap;
    }

    public List<String> getDNSSupportHosts() {
        return DNSSupportHosts;
    }

    public Application getApplication() {
        return application;
    }

    public boolean isDnsEnable() {
        return isDnsEnable;
    }

    public boolean isLogDebug() {
        return isLogDebug;
    }

    public int getNetThreadPoolSize() {
        return netThreadPoolSize;
    }

    public boolean isDnsDebugEnabled() {
        return isDnsDebugEnabled;
    }

    public Map<String, List<String>> getDebugDnsMap() {
        return debugDnsMap;
    }

    public static class Builder {
        private Application mApplication;
        private boolean mIsDnsEnable;
        private boolean mIsLogDebug;
        private int mNetThreadPoolSize;
        private List<String> mDNSSupportHosts;
        private boolean mIsDnsDebugEnabled;
        private Map<String, List<String>> mDebugDnsMap;

        public Builder(Application application) {
            mApplication = application;
        }

        public Builder dnsSupportHosts(List<String> hosts) {
            mDNSSupportHosts = hosts;
            return this;
        }

        public Builder dnsEnable(boolean isDnsEnable) {
            mIsDnsEnable = isDnsEnable;
            return this;
        }

        public Builder logDebug(boolean isLogDebug) {
            mIsLogDebug = isLogDebug;
            return this;
        }

        public Builder netThreadPoolSize(int netThreadPoolSize) {
            mNetThreadPoolSize = netThreadPoolSize;
            return this;
        }

        public Builder dnsDebugEnabled(boolean isDnsDebugEnabled) {
            mIsDnsDebugEnabled = isDnsDebugEnabled;
            return this;
        }

        public Builder debugDnsMap(Map<String, List<String>> debugDnsMap) {
            if (mDebugDnsMap == null) {
                mDebugDnsMap = new HashMap<>();
            }
            if (debugDnsMap != null) {
                mDebugDnsMap.putAll(debugDnsMap);
            }
            return this;
        }

        public CoreConfig build() {
            return new CoreConfig(this);
        }

    }
}
