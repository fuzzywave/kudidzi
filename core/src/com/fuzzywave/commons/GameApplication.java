package com.fuzzywave.commons;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.platform.LoggerApi;

public class GameApplication implements ApplicationListener {

    private final float maximumDelta = 1f / 60;
    private final float targetTimestep = 0.01f;

    public static LoggerApi logger;
    private float accumulator = 0f;
    private GameContainer gc;

    public GameApplication(GameContainer gc, LoggerApi logger) {
        this.gc = gc;
        GameApplication.logger = logger;
    }

    @Override
    public void create() {
        try {
            GameApplication.logger.init();
            gc.start();
        } catch (Exception e) {
            Gdx.app.error("CREATE", e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            gc.resize(width, height);
        } catch (Exception e) {
            Gdx.app.error("RESIZE", e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void render() {
        try {
            float delta = Gdx.graphics.getDeltaTime();
            if (delta > maximumDelta) {
                delta = maximumDelta;
            }
            accumulator += delta;
            while (accumulator >= targetTimestep) {
                gc.update(targetTimestep);
                accumulator -= targetTimestep;
            }
            gc.interpolate(accumulator / targetTimestep);

            gc.render();

        } catch (Exception e) {
            Gdx.app.error("RENDER", e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void resume() {
        try {
            gc.onResume();
        } catch (Exception e) {
            Gdx.app.error("RESUME", e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void pause() {
        try {
            gc.onPause();
        } catch (Exception e) {
            Gdx.app.error("PAUSE", e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        try {
            gc.onPause();
            gc.dispose();
        } catch (Exception e) {
            Gdx.app.error("DISPOSE", e.getMessage(), e);
            Gdx.app.exit();
        }
    }
}
