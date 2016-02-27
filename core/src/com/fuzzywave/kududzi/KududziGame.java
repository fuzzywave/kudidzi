package com.fuzzywave.kududzi;

import com.fuzzywave.commons.resources.ResourceManager;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.kududzi.gamestates.PlayGameState;
import com.fuzzywave.kududzi.gamestates.SplashGameState;

public class KududziGame extends com.fuzzywave.commons.ScreenBasedGameContainer {

    public Screen splashScreen;
    public Screen playGameScreen;

    @Override
    public void create() {

        super.create();

        // TODO create game states.
        GameState splashGameState = new SplashGameState(this);
        GameState playGameState = new PlayGameState(this);

        // TODO create game screens.
        splashScreen = new ScreenImpl(splashGameState);
        playGameScreen = new ScreenImpl(playGameState);

        // TODO load assets.

        // TODO switch to splash screen.
        setScreen(splashScreen);
    }

    @Override
    protected void initResourceManager() {
        KududziGame.resourceManager = new ResourceManager("data/assets.json");
    }



    @Override
    public void initialise() {
        // Add screen implementations to the game container.
    }

    @Override
    public int getInitialScreenId() {
        return 0;
    }

}
