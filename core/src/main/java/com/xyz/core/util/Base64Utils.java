package com.xyz.core.util;

import it.sauronsoftware.base64.Base64;

/**
 * Base64加密工具类
 * <p>
 * Created by jason on 2017/9/23.
 */
public class Base64Utils {

    public static byte[] encodeBase64(byte[] binaryData) {
        return Base64.encode(binaryData);
    }

    public static String encodeBase64(String source) {
        return Base64.encode(source);
    }

}
