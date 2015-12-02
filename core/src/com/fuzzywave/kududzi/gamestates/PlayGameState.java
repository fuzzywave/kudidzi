package com.fuzzywave.kududzi.gamestates;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fuzzywave.commons.gamestate.GameStateImpl;
import com.fuzzywave.kududzi.KududziGame;
import com.fuzzywave.kududzi.entity.systems.PhysicsTransformSystem;
import com.fuzzywave.kududzi.entity.systems.RenderingSystem;

public class PlayGameState extends GameStateImpl {

    private static final Color COLOR1 = Color.valueOf("fe8cFF");
    private static final Color COLOR2 = Color.valueOf("f836FF");
    private final KududziGame kududziGame;

    private Engine engine;

    public PlayGameState(KududziGame kududziGame) {
        this.kududziGame = kududziGame;
    }

    @Override
    public void init() {
        super.init();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);

        // TODO init ashley engine.
        engine = new PooledEngine();
        // TODO init entity factory.
        // TODO init assets.
    }

    @Override
    public void dispose(){
        super.dispose();
    }

    @Override
    public void update(){
        engine.update(delta);
    }

    @Override
    public void render() {
        PhysicsTransformSystem physicsTransformSystem = engine.getSystem(PhysicsTransformSystem.class);
        if(physicsTransformSystem != null){
            physicsTransformSystem.interpolate(alpha);
        }

        RenderingSystem renderingSystem = engine.getSystem(RenderingSystem.class);
        if(renderingSystem != null){
            renderingSystem.update(delta);
        }
    }
}
