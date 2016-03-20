package com.fuzzywave.kududzi;

import com.fuzzywave.commons.game.ScreenBasedGameContainer;
import com.fuzzywave.commons.resources.ResourceManager;
import com.fuzzywave.commons.screen.Screen;
import com.fuzzywave.kududzi.screen.GameScreen;
import com.fuzzywave.kududzi.screen.Screens;
import com.fuzzywave.kududzi.screen.SplashScreen;

public class KududziGame extends ScreenBasedGameContainer {


    @Override
    protected void initialise() {
        ResourceManager resourceManager = new ResourceManager("data/assets.json");
        //
        Screen splashScreen = new SplashScreen(resourceManager);
        addScreen(splashScreen);
        //
        Screen gameScreen = new GameScreen();
        addScreen(gameScreen);
    }

    @Override
    public int getInitialScreenId() {
        return Screens.SPLASH_SCREEN_ID;
    }
}
