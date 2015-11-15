package com.fuzzywave.commons.screen;


import com.fuzzywave.commons.gamestate.GameState;
import com.fuzzywave.commons.util.Parameters;

public class ScreenImpl implements Screen {

    private final GameState gameState;
    private final float dt = 0.01f;
    private final float maxFrameTime = 0.25f;
    private boolean paused = true;
    private boolean visible = false;
    private boolean inited = false;
    private float delta;
    private float accumulator;

    public ScreenImpl(GameState gameState) {
        this.gameState = gameState;
    }

    protected float getDelta() {
        return delta;
    }

    @Override
    public void setDelta(float delta) {
        this.delta = delta;
    }

    @Override
    public void init() {
        if (inited) {
            return;
        }
        inited = true;
        gameState.init();
    }

    @Override
    public void dispose() {
        if (!inited) {
            return;
        }
        inited = false;
        gameState.dispose();
    }

    @Override
    public void restart() {
        dispose();
        init();
    }

    @Override
    public void show() {
        if (visible) {
            return;
        }
        visible = true;
        gameState.show();
    }

    @Override
    public void hide() {
        if (!visible) {
            return;
        }
        visible = false;
        gameState.hide();
    }

    @Override
    public void pause() {
        if (paused) {
            return;
        }
        paused = true;
        gameState.pause();
    }

    @Override
    public void resume() {
        if (!paused) {
            return;
        }
        paused = false;
        gameState.resume();
    }

    @Override
    public void update() {
        if (paused) {
            return;
        }

        float frameTime = getDelta();
        if (frameTime > maxFrameTime) {
            frameTime = maxFrameTime;
        }

        accumulator += frameTime;

        while (accumulator >= dt) {
            gameState.setDelta(dt);
            gameState.update();

            accumulator -= dt;
        }

        // interpolation:
        float alpha = accumulator / dt;
        gameState.setAlpha(alpha);
    }

    @Override
    public void render() {
        if (!visible) {
            return;
        }
        gameState.setDelta(this.delta);
        gameState.render();
    }

    @Override
    public void resize(int width, int height) {
        gameState.resize(width, height);
    }

    @Override
    public Parameters getParameters() {
        return gameState.getParameters();
    }
}
