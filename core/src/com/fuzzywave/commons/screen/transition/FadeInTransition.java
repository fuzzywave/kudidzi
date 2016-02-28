package com.fuzzywave.commons.screen.transition;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.Screen;

/**
 * Implements a fade in transition.
 */
public class FadeInTransition implements Transition {

    private Color color;
    private float duration;

    /**
     * Default constructor - fade from black in 0.5 seconds
     */
    public FadeInTransition() {
        this(Color.BLACK);
    }

    /**
     * Constructs a fade in transition that lasts 0.5 seconds
     *
     * @param color The {@link Color} to fade from
     */
    public FadeInTransition(Color color) {
        this(color, 0.5f);
    }

    /**
     * Constructs a fade in transition
     *
     * @param color    The {@link Color} to fade from
     * @param duration The time in seconds to last
     */
    public FadeInTransition(Color color, float duration) {
        this.color = color;
        this.color.a = 1f;
        this.duration = duration;
    }

    @Override
    public void initialise(Screen outScreen, Screen inScreen) {
    }

    @Override
    public void update(GameContainer gc, float delta) {
        color.a -= (delta * 1.0f) / duration;
        if (color.a < 0) {
            color.a = 0;
        }
    }

    @Override
    public void preRender(GameContainer gc, Graphics g) {
    }

    @Override
    public void postRender(GameContainer gc, Graphics g) {
        g.shapeRenderer.begin();
        g.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        g.shapeRenderer.rect(0, 0, gc.getWidth(), gc.getHeight(), color, color, color, color);
        g.shapeRenderer.end();
    }

    @Override
    public boolean isFinished() {
        return color.a <= 0;
    }
}
