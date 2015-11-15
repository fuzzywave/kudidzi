package com.fuzzywave.commons.screen.transition.internal;

import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.transition.TransitionHandler;

public class LeaveTransition extends InternalScreenTransitionImpl {

    public LeaveTransition(Screen screen, float totalTime) {
        super(screen, totalTime);
    }

    public LeaveTransition(Screen screen, float totalTime, TransitionHandler transitionHandler) {
        super(screen, totalTime, transitionHandler);
    }

    public void init() {
        super.init();
        //getScreen().init();
        //getScreen().show();
        getTransitionHandler().onBegin();
    }

    public void dispose() {
        getScreen().pause();
        getScreen().hide();
        getTransitionHandler().onEnd();
    }

}
