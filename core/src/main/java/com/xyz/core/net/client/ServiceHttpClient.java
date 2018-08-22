package com.xyz.core.net.client;

/**
 * Created by jiasong on 2017/10/17.
 */
public class ServiceHttpClient extends BaseHttpClient {

    @Override
    protected String getSentryKey() {
        return REQUEST_TYPE_JSON;
    }

    @Override
    protected boolean printResponseBody() {
        return true;
    }
}
