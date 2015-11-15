package com.fuzzywave.commons.platform;


public interface LoggerApi {
    void init(String tag, int level);

    void info(String msg);

    void error(String msg);

    void error(String msg, Exception e);

    void debug(String msg);
}
