package com.fuzzywave.commons.screen.transition;


import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.transition.internal.EnterTransition;
import com.fuzzywave.commons.screen.transition.internal.InternalScreenTransition;
import com.fuzzywave.commons.screen.transition.internal.LeaveTransition;

public class ScreenTransition {
    private InternalScreenTransition currentTransition;
    private InternalScreenTransition leaveTransition;
    private InternalScreenTransition enterTransition;

    public ScreenTransition(Screen start, Screen end, float timeStart, float timeEnd) {
        this(new LeaveTransition(start, timeStart), new EnterTransition(end, timeEnd));
    }

    public ScreenTransition(InternalScreenTransition leave, InternalScreenTransition enter) {
        this.leaveTransition = leave;
        this.enterTransition = enter;
        currentTransition = leaveTransition;
    }

    public boolean isFinished() {
        return enterTransition.isFinished();
    }

    public Screen getCurrentScreen() {
        return currentTransition.getScreen();
    }

    public InternalScreenTransition getCurrentTransition() {
        return currentTransition;
    }

    public void start() {
        leaveTransition.init();
    }

    public void update(float delta) {
        if (isFinished()) {
            return;
        }
        updateEnterTransition(delta);
        updateLeaveTransition(delta);
    }

    private void updateLeaveTransition(float delta) {
        if (leaveTransition.isFinished()) {
            return;
        }

        leaveTransition.update(delta);
        if (leaveTransition.isFinished()) {
            currentTransition = enterTransition;
            leaveTransition.dispose();
            enterTransition.init();
        }
    }

    private void updateEnterTransition(float delta) {
        if (!leaveTransition.isFinished()) {
            return;
        }

        if (enterTransition.isFinished()) {
            return;
        }

        enterTransition.update(delta);
        if (enterTransition.isFinished()) {
            enterTransition.dispose();
        }
    }
}
