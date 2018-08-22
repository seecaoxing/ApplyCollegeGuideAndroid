package com.xyz.core.net.interceptor;

import android.text.TextUtils;

import com.xyz.core.net.HttpUtils;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import static com.xyz.core.net.HttpUtils.HEADER_REQUEST_TRACE_ID;

/**
 * Created by heq on 17/9/5.
 */

public class NTESLogEvent implements LogInterceptor.ILogEvent {

    private String mRequestTraceId;

    @Override
    public String generateRequestHeaderInfo(Request request) {
        if (request == null) {
            return "";
        }
        Headers headers = request.headers();
        //目前requestHeader中只打印X-NR-Trace-Id信息
        if (headers == null || TextUtils.isEmpty(headers.get(HEADER_REQUEST_TRACE_ID))) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HEADER_REQUEST_TRACE_ID).append(":").append(headers.get(HEADER_REQUEST_TRACE_ID)).append("\n");
        return stringBuilder.toString();
    }

    @Override
    public String generateResponseHeaderInfo(Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HttpUtils.HEADER_REQUEST_TRACE_ID).append(":").append(mRequestTraceId).append("\n(")
                .append(response.headers()).append(")");
        return stringBuilder.toString();
    }

    @Override
    public String generateHttpExceptionInfo(Request request, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<-- HTTP FAILED: ")
                .append(request.url()).append("\n")
                .append(HttpUtils.HEADER_REQUEST_TRACE_ID).append(":").append(mRequestTraceId)
                .append("\n").append(e);
        return stringBuilder.toString();
    }

    @Override
    public void beforeRequest(Request request) {
        mRequestTraceId = request.header(HttpUtils.HEADER_REQUEST_TRACE_ID);
    }
}
