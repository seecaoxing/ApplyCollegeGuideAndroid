package com.xyz.core.net.interceptor;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by heq on 17/9/5.
 */

public class DefaultLogEvent implements LogInterceptor.ILogEvent {

    @Override
    public String generateRequestHeaderInfo(Request request) {
        if (request != null && request.headers() != null) {
            return request.headers().toString();
        }
        return "";
    }

    @Override
    public String generateResponseHeaderInfo(Response response) {
        if (response != null && response.headers() != null) {
            return response.headers().toString();
        }
        return "";
    }

    @Override
    public String generateHttpExceptionInfo(Request request, Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<-- HTTP FAILED: ")
                .append(request.url()).append("\n")
                .append(e);
        return stringBuilder.toString();
    }

    @Override
    public void beforeRequest(Request request) {
    }
}
