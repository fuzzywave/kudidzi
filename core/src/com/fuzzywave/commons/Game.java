package com.fuzzywave.commons;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.platform.LoggerApi;
import com.fuzzywave.commons.resources.ResourceManager;

public abstract class Game implements ApplicationListener {

    public static LoggerApi logger;
    public static SpriteBatch spriteBatch;
    public static ShapeRenderer shapeRenderer;

    public static ResourceManager resourceManager;

    protected Screen screen;

    public static void setPlatformResolvers(LoggerApi logger) {
        Game.logger = logger;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        setScreen(screen, false);
    }

    @Override
    public void dispose() {
        if (screen == null) {
            return;
        }
        screen.hide();
        screen.pause();
        screen.dispose();

        Game.shapeRenderer.dispose();
        Game.spriteBatch.dispose();
        Game.resourceManager.dispose();
    }

    @Override
    public void pause() {
        if (screen != null) {
            screen.pause();
        }
    }

    @Override
    public void resume() {
        if (screen != null) {
            screen.resume();
        }
    }

    @Override
    public void render() {
        if (screen == null) {
            return;
        }
        screen.setDelta(Gdx.graphics.getRawDeltaTime());
        screen.update();
        screen.render();
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) {
            screen.resize(width, height);
        }
    }

    public void setScreen(Screen screen, boolean shouldDispose) {
        if (screen == null) {
            return;
        }

        Screen currentScreen = getScreen();

        if (currentScreen != null) {
            currentScreen.pause();
            currentScreen.hide();
            if (shouldDispose) {
                currentScreen.dispose();
            }
        }

        this.screen = screen;
        screen.init();
        screen.resume();
        screen.show();
    }

    @Override
    public void create() {
        Game.spriteBatch = new SpriteBatch();
        Game.shapeRenderer = new ShapeRenderer();

        // TODO Resource Manager
    }

    protected abstract void initResourceManager();
}
