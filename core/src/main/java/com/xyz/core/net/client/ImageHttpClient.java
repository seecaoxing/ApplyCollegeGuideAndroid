package com.xyz.core.net.client;

/**
 * Created by jiasong on 2017/10/17.
 */
public class ImageHttpClient extends BaseHttpClient {

    @Override
    protected String getSentryKey() {
        return REQUEST_TYPE_IMG;
    }

    @Override
    protected boolean printResponseBody() {
        return false;
    }
}
