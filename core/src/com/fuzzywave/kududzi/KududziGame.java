package com.fuzzywave.kududzi;

import com.fuzzywave.commons.gamestate.GameState;
import com.fuzzywave.commons.resources.ResourceManager;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.commons.screen.ScreenImpl;
import com.fuzzywave.kududzi.gamestates.SplashGameState;

public class KududziGame extends com.fuzzywave.commons.Game {

    public Screen splashScreen;

    @Override
    public void create() {

        super.create();

        // TODO create game states.
        GameState splashGameState = new SplashGameState();

        // TODO create game screens.
        splashScreen = new ScreenImpl(splashGameState);

        // TODO load assets.

        // TODO switch to splash screen.
        setScreen(splashScreen);
    }

    @Override
    protected void initResourceManager() {
        KududziGame.resourceManager = new ResourceManager("data/assets.json");
    }

    // TODO TransitionBuilder
}
