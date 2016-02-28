package com.fuzzywave.commons.screen;


import com.fuzzywave.commons.game.GameContainer;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.transition.Transition;

public interface Screen {

    /**
     * Initialises the screen.
     */
    void init(GameContainer gc);

    /**
     * Updates the screen.
     */
    void update(GameContainer gc, ScreenManager<? extends Screen> screenManager, float delta);

    /**
     * Interpolates the screen.
     */
    void interpolate(GameContainer gc, float alpha);

    /**
     * Renders the screen.
     */
    void render(GameContainer gc, Graphics g);

    /**
     * Called when the window's dimensions changes.
     */
    void onResize(int width, int height);

    /**
     * Called when the game is paused.
     */
    void onPause();

    /**
     * Called when the game is resumed.
     */
    void onResume();

    /**
     * Called when the game window is no longer active or visible.
     */
    void onHide();

    /**
     * Called when the game window becomes active or visible again.
     */
    void onShow();

    /**
     * Disposes the Screen.
     */
    void onDispose();

    /**
     * Restarts the Screen.
     */
    void onRestart();

    /**
     * Returns the identifier of the screen.
     */
    int getId();

    /**
     * Called before the transition in
     *
     * @param transitionIn The {@link Transition} in to this screen
     */
    void preTransitionIn(Transition transitionIn);

    /**
     * Called after the transition in
     *
     * @param transitionIn The {@link Transition} in to this screen
     */
    void postTransitionIn(Transition transitionIn);

    /**
     * Called before the transition out
     *
     * @param transitionOut The {@link Transition} out from this screen
     */
    void preTransitionOut(Transition transitionOut);

    /**
     * Called after the transition out
     *
     * @param transitionOut The {@link Transition} out from this screen
     */
    void postTransitionOut(Transition transitionOut);
}
