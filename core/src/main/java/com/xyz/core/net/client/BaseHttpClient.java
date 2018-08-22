package com.xyz.core.net.client;

import com.xyz.core.net.HttpUtils;
import com.xyz.core.net.interceptor.GzipInterceptor;
import com.xyz.core.net.interceptor.LogInterceptor;
import com.xyz.core.net.interceptor.NTESLogEvent;
import com.xyz.core.net.param.HeaderParam;
import com.xyz.core.util.DataUtils;
import com.xyz.core.util.SystemUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiasong on 2017/10/17.
 */
public abstract class BaseHttpClient {

    public static final String REQUEST_TYPE_IMG = "img";
    public static final String REQUEST_TYPE_JSON = "json";
    public static final String REQUEST_TYPE_VIDEO_STREAM = "video_stream";

    // 默认超时毫秒值
    private static final int DEFAULT_TIMEOUT_MS = 30 * 1000;
    // 每个域名允许同时请求的最大数量
    private static final int MAX_REQUESTS_PER_HOST = 10;

    private OkHttpClient client;

    /* package */ BaseHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(createDispatcher())
                .readTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new GzipInterceptor())
                // .addInterceptor(new BuiltInInterceptor())
                .addInterceptor(new LogInterceptor(getClass().getSimpleName(), printResponseBody(), new NTESLogEvent()));
        // .addInterceptor(new SentryInterceptor(getSentryKey()))
        // .dns(new DnsSetting(NewsHttpDns.getInstance()))
        client = builder.build();
    }

    private Dispatcher createDispatcher() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);
        return dispatcher;
    }

    protected abstract String getSentryKey();

    protected abstract boolean printResponseBody();

    public OkHttpClient getClient() {
        return client;
    }

    private class BuiltInInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originRequest = chain.request();
            Request.Builder builder = originRequest.newBuilder();

            builder.header(HeaderParam.USER_AGENT, HttpUtils.getUserAgent());
            builder.header(HeaderParam.TRACE_ID, SystemUtils.getTraceId(String.valueOf(originRequest.hashCode())));
            String httpDnsIp = getHttpDnsIp(chain.request());
            if (httpDnsIp != null) {
                builder.header(HeaderParam.HTTP_DNS_IP, httpDnsIp);
            }
            return chain.proceed(builder.build());
        }

        private String getHttpDnsIp(Request request) throws UnknownHostException {
            List<InetAddress> addresses = getClient().dns().lookup(request.url().host());
            if (DataUtils.valid(addresses)) {
                InetAddress item = DataUtils.getItemData(addresses, 0);
                return DataUtils.valid(item) ? item.getHostAddress() : "";
            }
            return null;
        }
    }

}
