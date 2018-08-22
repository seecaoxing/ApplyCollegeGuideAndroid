package com.xyz.core.util.string;

import android.text.Html;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * <p>
 * Created by jason on 2017/9/23.
 */
public final class StringUtils {

    /**
     * The digits for every supported radix.
     */
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] UPPER_CASE_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final String FILTER_HYPERLINK_REGEX = "((?<!['\"])(http|https)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*)";
    private static final String FILTER_HYPERLINK_REPLACE_STR = "<a href='%s'>链接</a>";
    private static final String[][] HTML_DECODE = new String[][]{
            {"&lt;", "<"},
            {"&rt;", ">"},
            {"&gt;", ">"},
            {"&quot;", "\\\""},
            {"&#039;", "'"},
            {"&nbsp;", " "},
            {"&nbsp", " "},
            {"<br>", "\n"},
            {"<BR>", "\n"},
            {"\r\n", "\n"},
            {"&#8826;", "•"},
            {"&#8226;", "•"},
            {"&#9642;", "•"},
            {"&amp;", "&"},
            {"<br/>", ""},
            {"<BR/>", ""},
            {"<BR/>", ""},
            {"&hellip;", ""},
            {"&#040;", "("},
            {"&#041;", ")"},
    };

    public static final boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }


    /**
     * Returns a new byte array containing the characters of this string encoded using the
     * given charset.
     * <p>
     * <p>The behavior when this string cannot be represented in the given charset
     * is to replace malformed input and unmappable characters with the charset's default
     * replacement byte array. Use {@link java.nio.charset.CharsetEncoder} for more control.
     */
    public static byte[] getBytes(String string, Charset charset) {
        try {
            return string.getBytes(charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String bytesToHexString(byte[] bytes, boolean upperCase) {
        char[] digits = upperCase ? UPPER_CASE_DIGITS : DIGITS;
        char[] buf = new char[bytes.length * 2];
        int c = 0;
        for (byte b : bytes) {
            buf[c++] = digits[(b >> 4) & 0xf];
            buf[c++] = digits[b & 0xf];
        }
        return new String(buf);
    }

    /**
     * MD5
     *
     * @param data
     * @return String
     */
    public static String md5(String data) {
        if (TextUtils.isEmpty(data)) {
            return data;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5bytes = messageDigest.digest(StringUtils.getBytes(data, Charset.forName("UTF-8")));
            return StringUtils.bytesToHexString(md5bytes, false);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * 替换HTML字符.
     */
    public static String htmlDecoder(String src) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }
        //see http://commons.apache.org/proper/commons-lang/download_lang.cgi
        return (new LookupTranslator(HTML_DECODE)).translate(src);

//        String dst = src;
//        dst = replaceAll(dst, "&lt;", "<");
//        dst = replaceAll(dst, "&rt;", ">");
//        dst = replaceAll(dst, "&gt;", ">");
//        dst = replaceAll(dst, "&quot;", "\"");
//        dst = replaceAll(dst, "&#039;", "'");
//        dst = replaceAll(dst, "&nbsp;", " ");
//        dst = replaceAll(dst, "&nbsp", " ");
//        dst = replaceAll(dst, "<br>", "\n");
//        dst = replaceAll(dst, "<BR>", "\n");
//        dst = replaceAll(dst, "\r\n", "\n");
//        dst = replaceAll(dst, "&#8826;", "•");
//        dst = replaceAll(dst, "&#8226;", "•");
//        dst = replaceAll(dst, "&#9642;", "•");
//        dst = replaceAll(dst, "&amp;", "&");
//        dst = replaceAll(dst, "<br/>", "");
//        dst = replaceAll(dst, "<BR/>", "");
//        dst = replaceAll(dst, "<BR/>", "");
//        dst = replaceAll(dst, "&hellip;", "");
//        dst = replaceAll(dst, "&#040;", "(");
//        dst = replaceAll(dst, "&#041;", ")");
//        return dst;
    }


    /**
     * 字符替换
     *
     * @param src
     * @param fnd
     * @param rep
     * @return String
     * @throws Exception
     */
    public static String replaceAll(String src, String fnd, String rep)
            throws Exception {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(fnd)) {
            return "";
        }

        String dst = src;

        int idx = dst.indexOf(fnd);

        while (idx >= 0) {
            dst = dst.substring(0, idx) + rep
                    + dst.substring(idx + fnd.length(), dst.length());
            idx = dst.indexOf(fnd, idx + rep.length());
        }

        return dst;
    }


    /**
     * @param s         源字符串
     * @param srcString 需要替换的字符串
     * @param dstString 替换后的字符串
     * @return
     */
    public static String replace(String s, String srcString, String dstString) {
        if (s == null || s.equals("")) {
            return "";
        }

        String d = s;
        try {
            d = replaceAll(d, srcString, dstString);
        } catch (Exception e) {
        }
        return d;
    }


    public static String formatBody(String body) {

        if (body == null || body.equals("")) {
            return "";
        }
        try {
            body = replaceAll(body, "&gt;", ">");
            body = replaceAll(body, "　 ", "");
            return Html.fromHtml(body).toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 过滤html中p标签后面的空格
     *
     * @param str
     * @return
     */
    public static String filterPSpace(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        return str.replaceAll("<p>\\s*", "<p>");
    }

    /**
     * 过滤html最开始的空格
     *
     * @param str
     * @return
     */
    public static String filterStartSpace(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        // 防止最开始是需要替换的标签（如视频、图片）前有空格，导致挤出空间
        Pattern p = Pattern.compile("^\\s*");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String findStr = m.group();
            str = str.replace(findStr, "");
        }

        return str;
    }

    /**
     * 过滤换行符br，替换为空格
     *
     * @param str
     * @return
     */
    public static String replaceBrChar(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replaceAll("<br>", " ");
            str = str.replaceAll("<br/>", " ");
            str = str.replaceAll("<BR>", " ");
            str = str.replaceAll("<BR/>", " ");
        }
        return str;
    }

    /**
     * 过滤Body字段中非标准超链接
     *
     * @param body
     * @return
     */
    public static String filterHyperlink4Body(String body) {
        if (TextUtils.isEmpty(body)) {
            return body;
        }

        Pattern p = Pattern.compile(FILTER_HYPERLINK_REGEX);
        Matcher m = p.matcher(body);
        while (m.find()) {
            String findStr = m.group();
            body = body.replace(findStr, String.format(FILTER_HYPERLINK_REPLACE_STR, findStr));
        }

        return body;
    }
}