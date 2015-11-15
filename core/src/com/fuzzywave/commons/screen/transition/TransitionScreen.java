package com.fuzzywave.commons.screen.transition;


import com.fuzzywave.commons.gamestate.GameStateImpl;
import com.fuzzywave.commons.screen.ScreenImpl;
import com.fuzzywave.commons.screen.transition.internal.InternalScreenTransition;

public class TransitionScreen extends ScreenImpl {

    protected final ScreenTransition screenTransition;

    public TransitionScreen(ScreenTransition screenTransition) {
        super(new GameStateImpl());
        this.screenTransition = screenTransition;
    }

    @Override
    public void init() {
        super.init();
        screenTransition.start();
    }

    @Override
    public void update() {
        super.update();
        screenTransition.update(getDelta());
    }

    @Override
    public void render() {
        super.render();
        InternalScreenTransition transition = screenTransition.getCurrentTransition();
        transition.preRender(getDelta());
        screenTransition.getCurrentScreen().render();
        transition.postRender(getDelta());
    }
}
