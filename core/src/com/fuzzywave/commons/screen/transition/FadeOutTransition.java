package com.fuzzywave.commons.screen.transition;

import com.badlogic.gdx.graphics.Color;
import com.fuzzywave.commons.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.Screen;

/**
 * Implements a fade out transition.
 */
public class FadeOutTransition implements Transition {

    private Color color;
    private float duration;

    /**
     * Default constructor - fade to black in 0.5 seconds
     */
    public FadeOutTransition() {
        this(Color.BLACK);
    }

    /**
     * Constructs a fade out transition that lasts 0.5 seconds
     *
     * @param color The {@link Color} to fade to
     */
    public FadeOutTransition(Color color) {
        this(color, 0.5f);
    }

    /**
     * Constructs a fade out transition
     *
     * @param color    The {@link Color} to fade to
     * @param duration The time in seconds to last
     */
    public FadeOutTransition(Color color, float duration) {
        this.color = color;
        this.color.a = 0f;
        this.duration = duration;
    }

    @Override
    public void initialise(Screen outScreen, Screen inScreen) {
    }

    @Override
    public void update(GameContainer gc, float delta) {
        color.a += (delta * 1.0f) / duration;
        if (color.a > 1f) {
            color.a = 1f;
        }
    }

    @Override
    public void preRender(GameContainer gc, Graphics g) {
    }

    @Override
    public void postRender(GameContainer gc, Graphics g) {
        Color old = g.getColor();
        g.setColor(color);
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        g.setColor(old);
    }

    @Override
    public boolean isFinished() {
        return color.a >= 1f;
    }
}
