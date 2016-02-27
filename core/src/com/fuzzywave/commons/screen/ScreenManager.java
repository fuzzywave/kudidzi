package com.fuzzywave.commons.screen;


import com.fuzzywave.commons.GameContainer;
import com.fuzzywave.commons.GameResizeListener;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.transition.Transition;

public interface ScreenManager<T extends Screen> extends GameResizeListener {

    void update(GameContainer gc, float delta);

    void interpolate(GameContainer gc, float alpha);

    void render(GameContainer gc, Graphics g);

    void onPause();

    void onResume();

    void enterGameScreen(int id, Transition transitionOut,
                         Transition transitionIn);

    void addGameScreen(T screen);

    T getGameScreen(int id);

    boolean isTransitioning();
}
