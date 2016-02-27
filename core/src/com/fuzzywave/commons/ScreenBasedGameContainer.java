package com.fuzzywave.commons;


import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.commons.screen.BasicScreenManager;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.ScreenManager;
import com.fuzzywave.commons.screen.transition.Transition;

public abstract class ScreenBasedGameContainer extends GameContainer {

    private ScreenManager<Screen> screenManager;

    /**
     * Returns the identifier of the {@link Screen} that should be shown
     * when the game starts.
     */
    public abstract int getInitialScreenId();

    @Override
    public void update(float delta) {
        screenManager.update(this, delta);
    }

    @Override
    public void interpolate(float alpha) {
        screenManager.interpolate(this, alpha);
    }

    @Override
    public void render(Graphics g) {
        screenManager.render(this, g);
    }

    @Override
    public void onPause() {
        screenManager.onPause();
    }

    @Override
    public void onResume() {
        screenManager.onResume();
    }

    /**
     * Add a {@link Screen} to this game
     *
     * @param screen The {@link Screen} to be added
     */
    public void addScreen(Screen screen) {
        screen.init(this);
        screenManager.addGameScreen(screen);
    }

    /**
     * Begins a transition to a new {@link Screen}
     *
     * @param id            The id of the {@link Screen} to transition to
     * @param transitionOut The outgoing {@link Transition}, e.g. fade out
     * @param transitionIn  The incoming {@link Transition}, e.g. fade in
     */
    public void enterGameScreen(int id, Transition transitionOut,
                                Transition transitionIn) {
        screenManager.enterGameScreen(id, transitionOut, transitionIn);
    }

    @Override
    protected void preinit() {
        super.preinit();
        screenManager = new BasicScreenManager<Screen>();
        addResizeListener(screenManager);
    }

    @Override
    protected void postinit() {
        super.postinit();
        screenManager.enterGameScreen(getInitialScreenId(), null, null);
    }
}
