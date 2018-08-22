package com.xyz.core.log;


import java.util.Iterator;
import java.util.Map;

/**
 * 日志工具
 * 1.添加日志输出选项.控制日志输出位置
 * 2.添加文件日志功能.(因进程问题.现UI与Service只能打到不同的文件中)
 * 3.控制单个日志文件最大限制.由LOG_MAXSIZE常量控制,保留两个最新日志文件
 * 4.文件日志输出目标  /data/data/%packetname%/files/
 */
public class NTLog {

    private static Logger mLog = new NTLogger();

    public static void setLogger(Logger log) {
        mLog = log;
    }

    private NTLog() {

    }

    public static void init(boolean isDebug, String packetName) {
        if (mLog instanceof FileLogger) {
            ((FileLogger) mLog).init(isDebug, packetName);
        }
    }

    public static void v(String tag, String msg) {
        mLog.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        mLog.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        mLog.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        mLog.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        mLog.e(tag, msg);
    }

    public static void e(String tag, Throwable throwable) {
        mLog.e(tag, throwable);
    }


    /**
     * 打开或关闭系统日志打印到文件
     *
     * @param openSystemLog
     */
    public static void systemLogOpenOrNot(boolean openSystemLog) {
        if (mLog instanceof FileLogger) {
            ((FileLogger) mLog).setSystemLogSwitch(openSystemLog);
        }
    }

    /**
     * 修改日志打印等级
     *
     * @param level
     */
    public static void modifyLogLevel(int level) {
        if (mLog instanceof FileLogger) {
            ((FileLogger) mLog).modifyLogLevel(level);
        }
    }

    /**
     * 修改日志打印文件大小
     *
     * @param size
     */
    public static void modifyLogSize(long size) {
        if (mLog instanceof FileLogger) {
            ((FileLogger) mLog).modifyLogSize(size);
        }
    }

    public static String exportLogFile() {
        if (mLog instanceof FileLogger) {
            return ((FileLogger) mLog).exportLogFile();
        } else {
            return null;
        }
    }

    public static <K, V> void printMap(String tag, Map<K, V> map, String msgPrefix, boolean isDebug) {
        if (map == null || map.isEmpty()) {
            if (isDebug) {
                d(tag, "Map is null or empty!!");
            } else {
                i(tag, "Map is null or empty!!");
            }
        } else {
            if (msgPrefix == null) {
                msgPrefix = "";
            }
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            while (iterator != null && iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (entry != null) {
                    if (isDebug) {
                        d(tag, msgPrefix + "(" + entry.getKey() + " : " + entry.getValue() + ")");
                    } else {
                        i(tag, msgPrefix + "(" + entry.getKey() + " : " + entry.getValue() + ")");
                    }
                }
            }
        }
    }

    public static void logStackTrace(String tag, int maxLine, boolean isDebug) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 2, size = Math.min(stackTraceElements.length, maxLine); i < size; i++) {
            if (stackTraceElements[i] != null) {
                if (isDebug) {
                    d(tag, stackTraceElements[i].toString());
                } else {
                    i(tag, stackTraceElements[i].toString());
                }
            }
        }
    }
}
