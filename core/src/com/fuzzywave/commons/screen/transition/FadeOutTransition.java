package com.fuzzywave.commons.screen.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fuzzywave.commons.Game;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.transition.internal.LeaveTransition;

public class FadeOutTransition extends LeaveTransition {

    private final float initialTotalTime;

    private final Color startColor = new Color(0f, 0f, 0f, 0f);
    private final Color endColor = new Color(0f, 0f, 0f, 1f);
    private Color currentColor = new Color(0f, 0f, 0f, 0f);

    public FadeOutTransition(Screen screen, float totalTime) {
        super(screen, totalTime);
        this.initialTotalTime = totalTime;
    }

    public FadeOutTransition(Screen screen, float totalTime, TransitionHandler transitionHandler) {
        super(screen, totalTime, transitionHandler);
        this.initialTotalTime = totalTime;
    }

    @Override
    public void init() {
        super.init();
        currentColor.set(startColor);
    }

    @Override
    public void postRender(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Game.shapeRenderer.setColor(currentColor);
        Game.shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Game.shapeRenderer.rect(-Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Game.shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void internalUpdate(float delta) {
        super.internalUpdate(delta);
        currentColor.set(startColor);

        float elapsedTime = Math.min(initialTotalTime - totalTime, initialTotalTime);
        float t = elapsedTime / initialTotalTime;
        currentColor.lerp(endColor, t);
    }
}
