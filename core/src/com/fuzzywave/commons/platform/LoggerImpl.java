package com.fuzzywave.commons.platform;

import com.badlogic.gdx.Gdx;

public class LoggerImpl implements LoggerApi {

    private String tag;

    @Override
    public void init(String tag, int level) {
        this.tag = tag;
        Gdx.app.setLogLevel(level);
    }

    @Override
    public void info(String msg) {
        Gdx.app.log(tag, msg);
    }

    @Override
    public void error(String msg) {
        Gdx.app.error(tag, msg);
    }

    @Override
    public void error(String msg, Exception e) {
        Gdx.app.error(tag, msg, e);
    }

    @Override
    public void debug(String msg) {
        Gdx.app.debug(tag, msg);
    }
}
