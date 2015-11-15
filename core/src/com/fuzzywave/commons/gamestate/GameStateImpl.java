package com.fuzzywave.commons.gamestate;


import com.fuzzywave.commons.util.Parameters;
import com.fuzzywave.commons.util.StringParametersMapImpl;

public class GameStateImpl implements GameState {

    protected float delta;
    private float alpha;

    private Parameters parameters = new StringParametersMapImpl();

    @Override
    public void init() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void setDelta(float delta) {
        this.delta = delta;
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public Parameters getParameters() {
        return parameters;
    }
}
