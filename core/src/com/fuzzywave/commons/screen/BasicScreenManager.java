package com.fuzzywave.commons.screen;


import com.fuzzywave.commons.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.transition.NullTransition;
import com.fuzzywave.commons.screen.transition.Transition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BasicScreenManager<T extends Screen> implements
        ScreenManager<T> {
    protected T currentScreen, nextScreen;
    protected Transition transitionIn, transitionOut;
    private Map<Integer, T> gameScreens;

    /**
     * Constructor
     */
    public BasicScreenManager() {
        gameScreens = new ConcurrentHashMap<Integer, T>();
    }

    @Override
    public void update(GameContainer gc, float delta) {
        if (transitionOut != null) {
            transitionOut.update(gc, delta);
            if (transitionOut.isFinished()) {
                if (currentScreen != null) {
                    currentScreen.postTransitionOut(transitionOut);
                }
                Screen oldScreen = currentScreen;
                currentScreen = nextScreen;
                nextScreen = null;
                transitionOut = null;

                if (transitionIn != null) {
                    currentScreen.preTransitionIn(transitionIn);
                }
            } else {
                return;
            }
        }

        if (transitionIn != null) {
            transitionIn.update(gc, delta);
            if (transitionIn.isFinished()) {
                currentScreen.postTransitionIn(transitionIn);
                transitionIn = null;
            } else {
                return;
            }
        }

        currentScreen.update(gc, this, delta);
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        if (currentScreen != null) {
            currentScreen.interpolate(gc, alpha);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        if (transitionOut != null) {
            transitionOut.preRender(gc, g);
        } else if (transitionIn != null) {
            transitionIn.preRender(gc, g);
        }

        if (currentScreen != null) {
            currentScreen.render(gc, g);
        }

        if (transitionOut != null) {
            transitionOut.postRender(gc, g);
        } else if (transitionIn != null) {
            transitionIn.postRender(gc, g);
        }
    }

    @Override
    public void enterGameScreen(int id, Transition transitionOut,
                                Transition transitionIn) {
        if (transitionOut == null) {
            transitionOut = new NullTransition();
        }
        if (transitionIn == null) {
            transitionIn = new NullTransition();
        }
        this.transitionIn = transitionIn;
        this.transitionOut = transitionOut;

        this.nextScreen = gameScreens.get(id);
        this.transitionOut.initialise(currentScreen, nextScreen);

        if (currentScreen != null) {
            currentScreen.preTransitionOut(transitionOut);
        }
    }

    @Override
    public void addGameScreen(T screen) {
        this.gameScreens.put(screen.getId(), screen);
    }

    @Override
    public T getGameScreen(int id) {
        return this.gameScreens.get(id);
    }

    @Override
    public boolean isTransitioning() {
        return transitionIn != null || transitionOut != null;
    }

    @Override
    public void onResize(int width, int height) {
        if (currentScreen == null) {
            return;
        }
        currentScreen.onResize(width, height);
    }

    @Override
    public void onPause() {
        if (currentScreen == null) {
            return;
        }
        currentScreen.onPause();
    }

    @Override
    public void onResume() {
        if (currentScreen == null) {
            return;
        }
        currentScreen.onResume();
    }
}
