package com.fuzzywave.kududzi.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fuzzywave.commons.GameApplication;
import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.ScreenManager;
import com.fuzzywave.commons.screen.transition.Transition;
import com.fuzzywave.kududzi.entity.systems.PhysicsTransformSystem;
import com.fuzzywave.kududzi.entity.systems.RenderingSystem;


public class GameScreen implements Screen {

    private final int SCENE_WIDTH = 720;
    private final int SCENE_HEIGHT = 1280;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Engine engine;

    @Override
    public void init(GameContainer gc) {
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
        this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //
        //
        engine = new Engine();
        engine.addSystem(new PhysicsTransformSystem(0));
        engine.addSystem(new RenderingSystem(gc.getGraphics(), 99));
        //
        //

        engine.getSystem(RenderingSystem.class).setProcessing(false);
    }

    @Override
    public void update(GameContainer gc,
                       ScreenManager<? extends Screen> screenManager,
                       float delta) {
        engine.update(delta);
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        PhysicsTransformSystem physicsTransformSystem =
                engine.getSystem(PhysicsTransformSystem.class);
        if ((physicsTransformSystem != null) && (physicsTransformSystem.checkProcessing())) {
            physicsTransformSystem.interpolate(alpha);
        } else {
            GameApplication.logger.error("There is no PhysicsTransformSystem.");
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.setProjectionMatrix(camera.combined);

        RenderingSystem renderingSystem = engine.getSystem(RenderingSystem.class);
        if (renderingSystem != null) {
            renderingSystem.update(1.0f);
        }else{
            GameApplication.logger.error("There is no RenderingSystem.");
        }
    }

    @Override
    public void onResize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDispose() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public int getId() {
        return Screens.GAME_SCREEN_ID;
    }

    @Override
    public void preTransitionIn(Transition transitionIn) {

    }

    @Override
    public void postTransitionIn(Transition transitionIn) {

    }

    @Override
    public void preTransitionOut(Transition transitionOut) {

    }

    @Override
    public void postTransitionOut(Transition transitionOut) {

    }
}
