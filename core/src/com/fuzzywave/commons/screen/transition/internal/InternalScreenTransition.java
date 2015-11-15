package com.fuzzywave.commons.screen.transition.internal;


import com.fuzzywave.commons.screen.Screen;

public interface InternalScreenTransition {

    void init();

    void dispose();

    void preRender(float delta);

    void postRender(float delta);

    void update(float delta);

    boolean isFinished();

    Screen getScreen();
}
