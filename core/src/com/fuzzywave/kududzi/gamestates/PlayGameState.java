package com.fuzzywave.kududzi.gamestates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fuzzywave.kududzi.KududziGame;

public class PlayGameState extends GameStateImpl {

    private static final Color COLOR1 = Color.valueOf("fe8cFF");
    private static final Color COLOR2 = Color.valueOf("f836FF");
    private final KududziGame kududziGame;


    public PlayGameState(KududziGame kududziGame) {
        this.kududziGame = kududziGame;
    }

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, this.camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        KududziGame.shapeRenderer.setProjectionMatrix(this.camera.combined);

        KududziGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        KududziGame.shapeRenderer.rect(-this.viewport.getWorldWidth() / 2,
                -this.viewport.getWorldHeight() / 2,
                this.viewport.getWorldWidth(),
                this.viewport.getWorldHeight(),
                PlayGameState.COLOR1,
                PlayGameState.COLOR2,
                PlayGameState.COLOR1,
                PlayGameState.COLOR2);
        KududziGame.shapeRenderer.end();
    }
}
