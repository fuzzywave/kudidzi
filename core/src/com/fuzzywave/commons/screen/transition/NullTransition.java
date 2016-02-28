package com.fuzzywave.commons.screen.transition;

import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.Screen;

/**
 * A {@link Transition} which does nothing.
 */
public class NullTransition implements Transition {

    @Override
    public void initialise(Screen outScreen, Screen inScreen) {
    }

    @Override
    public void update(GameContainer gc, float delta) {
    }

    @Override
    public void preRender(GameContainer gc, Graphics g) {
    }

    @Override
    public void postRender(GameContainer gc, Graphics g) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
