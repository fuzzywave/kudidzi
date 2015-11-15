package com.fuzzywave.commons.screen.transition.internal;


import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.transition.TransitionHandler;

public class InternalScreenTransitionImpl implements InternalScreenTransition {

    protected final Screen screen;
    private final TransitionHandler transitionHandler;

    private boolean finished;
    private boolean started;

    protected float totalTime;

    public InternalScreenTransitionImpl(Screen screen, float totalTime) {
        this(screen, totalTime, new TransitionHandler());
    }

    public InternalScreenTransitionImpl(Screen screen, float totalTime, TransitionHandler transitionHandler) {
        this.screen = screen;
        this.totalTime = totalTime;
        this.transitionHandler = transitionHandler;
    }

    protected TransitionHandler getTransitionHandler() {
        return transitionHandler;
    }

    @Override
    public void init() {
        started = true;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void preRender(float delta) {

    }

    @Override
    public void postRender(float delta) {

    }

    @Override
    public void update(float delta) {
        if (!started) {
            return;
        }
        internalUpdate(delta);
    }

    protected void internalUpdate(float delta) {
        if (isFinished()) {
            return;
        }
        totalTime -= delta;
        finished = totalTime <= 0f;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public Screen getScreen() {
        return screen;
    }
}
