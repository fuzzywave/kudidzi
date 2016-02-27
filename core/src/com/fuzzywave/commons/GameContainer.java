package com.fuzzywave.commons;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GameContainer {
    public static final float MAXIMUM_DELTA = (1f / 60f);

    protected int width, height;
    protected Graphics graphics;
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;

    private float accumulator = 0f;
    private float targetDelta = 0.01f;
    private boolean isInitialised = false;
    private List<GameResizeListener> gameResizeListeners;

    /**
     * Initialse the game
     */
    public abstract void initialise();

    /**
     * Update the game
     *
     * @param delta The time in seconds since the last update
     */
    public abstract void update(float delta);

    /**
     * Interpolate the game state
     *
     * @param alpha The alpha value to use during interpolation
     */
    public abstract void interpolate(float alpha);

    /**
     * Render the game
     *
     * @param g The {@link Graphics} context available for rendering
     */
    public abstract void render(Graphics g);

    public abstract void onPause();

    public abstract void onResume();

    public void render() {
        graphics.preRender(width, height);
        render(graphics);
        graphics.postRender();
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        for (GameResizeListener listener : gameResizeListeners) {
            listener.onResize(width, height);
        }
    }

    /**
     * Internal pre-initialisation code
     */
    protected void preinit() {
        this.gameResizeListeners = new ArrayList<GameResizeListener>(1);
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        this.graphics = new Graphics(spriteBatch, shapeRenderer);
    }

    /**
     * Internal post-initialisation code
     */
    protected void postinit() {
    }

    public void start() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();

        if (!isInitialised) {
            preinit();
            initialise();
            postinit();
            isInitialised = true;
        }
    }

    public void dispose() {

    }

    public void addResizeListener(GameResizeListener listener) {
        gameResizeListeners.add(listener);
    }

    public void removeResizeListener(GameResizeListener listener) {
        gameResizeListeners.remove(listener);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
