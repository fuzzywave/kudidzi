package com.fuzzywave.kududzi;

import com.fuzzywave.commons.resources.ResourceManager;

public class KududziGame extends com.fuzzywave.commons.Game {

    @Override
    public void create() {

        super.create();

        // TODO create game states.
        // TODO create game screens.

        // TODO load assets.

        // TODO switch to splash screen.
    }

    @Override
    protected void initResourceManager() {
        KududziGame.resourceManager = new ResourceManager("data/assets.json");
        KududziGame.resourceManager.loadGroup("splash_screen");
        KududziGame.resourceManager.finishLoading();
    }

    // TODO TransitionBuilder
}
