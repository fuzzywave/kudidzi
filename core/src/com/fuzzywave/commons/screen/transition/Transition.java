package com.fuzzywave.commons.screen.transition;


import com.fuzzywave.commons.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.Screen;

/**
 * Interface to screen transitions implementations.
 */
public interface Transition {

    void initialise(Screen outScreen, Screen inScreen);

    void update(GameContainer gc, float delta);

    void preRender(GameContainer gc, Graphics g);

    void postRender(GameContainer gc, Graphics g);

    boolean isFinished();

}
