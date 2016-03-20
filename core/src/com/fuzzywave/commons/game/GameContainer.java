package com.fuzzywave.commons.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.GameResizeListener;
import com.fuzzywave.commons.graphics.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class GameContainer {

    protected int width, height;
    protected Graphics graphics;
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;

    private boolean isInitialised = false;
    private List<GameResizeListener> gameResizeListeners;


    /**
     * Initialse the game
     */
    protected abstract void initialise();

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
    protected abstract void render(Graphics g);

    public abstract void onPause();

    public abstract void onResume();

    /**
     * Render the game
     */
    public void render() {
        graphics.preRender();
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

    public Graphics getGraphics() {
        return graphics;
    }
}
