package com.fuzzywave.commons.screen.transition;


import com.fuzzywave.commons.Game;
import com.fuzzywave.commons.screen.Screen;

public class ScreenTransitionBuilder {

    private final Screen screen;
    private final Game game;

    float leaveTime;
    float enterTime;

    boolean shouldDisposeCurrentScreen;
    boolean shouldRestartNextScreen;
    TransitionHandler leaveTransitionHandler = new TransitionHandler();
    TransitionHandler enterTransitionHandler = new TransitionHandler();
    private boolean transitioning = false;

    public ScreenTransitionBuilder(final Game game, final Screen screen) {
        this.game = game;
        this.screen = screen;
        this.leaveTransitionHandler = new TransitionHandler();
        this.leaveTime = 0.25f;
        this.enterTime = 0.25f;
    }

    public ScreenTransitionBuilder leaveTime(float leaveTime) {
        this.leaveTime = leaveTime;
        return this;
    }

    public ScreenTransitionBuilder enterTime(float enterTime) {
        this.enterTime = enterTime;
        return this;
    }

    public ScreenTransitionBuilder disposeCurrent() {
        this.shouldDisposeCurrentScreen = true;
        return this;
    }

    public ScreenTransitionBuilder disposeCurrent(boolean disposeCurrent) {
        this.shouldDisposeCurrentScreen = disposeCurrent;
        return this;
    }

    public ScreenTransitionBuilder restartScreen() {
        this.shouldRestartNextScreen = true;
        return this;
    }

    public ScreenTransitionBuilder restartScreen(boolean restart) {
        this.shouldRestartNextScreen = restart;
        return this;
    }

    public ScreenTransitionBuilder leaveTransitionHandler(TransitionHandler transitionHandler) {
        this.leaveTransitionHandler = transitionHandler;
        return this;
    }

    public ScreenTransitionBuilder enterTransitionHandler(TransitionHandler transitionHandler) {
        this.enterTransitionHandler = transitionHandler;
        return this;
    }

    public ScreenTransitionBuilder parameter(String key, Object value) {
        screen.getParameters().put(key, value);
        return this;
    }

    public void start() {
        if (transitioning) {
            Game.logger.info("Can't start a new transition.");
            return;
        }

        transitioning = true;
        final Screen currentScreen = game.getScreen();
        game.setScreen(new TransitionScreen(new ScreenTransition(new FadeOutTransition(currentScreen, leaveTime, new TransitionHandler() {

            @Override
            public void onBegin() {
                super.onBegin();
                leaveTransitionHandler.onBegin();
            }

            @Override
            public void onEnd() {
                super.onEnd();
                leaveTransitionHandler.onEnd();
                if (shouldRestartNextScreen) {
                    screen.dispose();
                }
            }

        }), new FadeInTransition(screen, enterTime, new TransitionHandler() {

            @Override
            public void onBegin() {
                super.onBegin();
                enterTransitionHandler.onBegin();
            }

            public void onEnd() {
                super.onEnd();
                enterTransitionHandler.onEnd();
                game.setScreen(screen, true);
                // disposes current transition screen, not previous screen.
                if (shouldDisposeCurrentScreen) {
                    currentScreen.dispose();
                }
                transitioning = false;
            }
        }))));
    }
}
