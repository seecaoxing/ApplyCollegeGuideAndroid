package com.xyz.core.log;

/**
 * Created by taiwei on 2017/9/29.
 */

public interface FileLogger extends Logger {

    void init(boolean isDebug, String packageName);

    void setSystemLogSwitch(boolean openSystemLog);

    void modifyLogLevel(int level);

    void modifyLogSize(long size);

    String exportLogFile();

}
