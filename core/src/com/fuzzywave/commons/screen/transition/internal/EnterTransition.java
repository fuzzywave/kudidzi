package com.fuzzywave.commons.screen.transition.internal;

import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.transition.TransitionHandler;

public class EnterTransition extends InternalScreenTransitionImpl {

    public EnterTransition(Screen screen, float totalTime) {
        super(screen, totalTime);
    }

    public EnterTransition(Screen screen, float totalTime, TransitionHandler transitionHandler) {
        super(screen, totalTime, transitionHandler);
    }

    public void init() {
        super.init();
        getScreen().init();
        getScreen().show();
        getScreen().resume();
        getTransitionHandler().onBegin();
    }

    public void dispose() {
        getTransitionHandler().onEnd();
    }

}
