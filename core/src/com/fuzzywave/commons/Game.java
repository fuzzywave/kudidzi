package com.fuzzywave.commons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.platform.LoggerApi;
import com.fuzzywave.commons.resources.ResourceManager;
import com.fuzzywave.commons.screen.Screen;

public abstract class Game implements ApplicationListener {

    public static LoggerApi logger;
    public static SpriteBatch spriteBatch;
    public static ShapeRenderer shapeRenderer;

    public static ResourceManager resourceManager;

    protected Screen screen;

    public static void setPlatformResolvers(LoggerApi logger) {
        Game.logger = logger;
    }


    @Override
    public void dispose() {
        try {
            if (screen == null) {
                return;
            }
            screen.hide();
            screen.pause();
            screen.dispose();

            Game.shapeRenderer.dispose();
            Game.spriteBatch.dispose();
            Game.resourceManager.dispose();
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void pause() {
        try {
            if (screen != null) {
                screen.pause();
            }
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resume() {
        try {
            if (screen != null) {
                screen.resume();
            }
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void render() {
        try {
            if (screen == null) {
                return;
            }
            screen.setDelta(Gdx.graphics.getRawDeltaTime());
            screen.update();
            screen.render();
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            if (screen != null) {
                screen.resize(width, height);
            }
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
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
        try {
            Game.logger.init();
            Game.spriteBatch = new SpriteBatch();
            Game.shapeRenderer = new ShapeRenderer();

            initResourceManager();
        } catch (Exception e) {
            Game.logger.error(e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    protected abstract void initResourceManager();
}
