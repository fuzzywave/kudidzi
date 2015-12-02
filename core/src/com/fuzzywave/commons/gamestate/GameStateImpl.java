package com.fuzzywave.commons.gamestate;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fuzzywave.commons.util.Parameters;
import com.fuzzywave.commons.util.StringParametersMapImpl;

public class GameStateImpl implements GameState {

    protected static final int SCENE_WIDTH = 1280;
    protected static final int SCENE_HEIGHT = 720;

    protected Camera camera;
    protected Viewport viewport;

    protected float delta;
    protected float alpha;

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
        this.viewport.update(width, height);
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

    @Override
    public int getViewportWidth(){
        return Math.round(viewport.getWorldWidth());
    }

    @Override
    public int getViewportHeight(){
        return Math.round(viewport.getWorldHeight());
    }
}
