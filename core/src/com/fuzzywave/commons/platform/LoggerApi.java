package com.fuzzywave.commons.platform;


public interface LoggerApi {
    void init();

    void info(String msg);

    void error(String msg);

    void error(String msg, Exception e);

    void debug(String msg);
}
