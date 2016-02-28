package com.fuzzywave.kududzi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.fuzzywave.commons.GameApplication;
import com.fuzzywave.commons.platform.LoggerImpl;
import com.fuzzywave.commons.util.ScreenConfig;
import com.fuzzywave.commons.util.SpritePacker;
import com.fuzzywave.kududzi.KududziGame;

public class DesktopLauncher {
    public static void main(String[] arg) {

        SpritePacker.packTexture("../../assets/game",
                "data/game",
                "game");

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        ScreenConfig.setScreenSize(ScreenConfig.ScreenSize.NORMAL,
                ScreenConfig.ScreenDensity.HDPI,
                config);

        LoggerImpl logger = new LoggerImpl("KududziDesktop", Logger.DEBUG);
        KududziGame kududziGame = new KududziGame();
        new LwjglApplication(new GameApplication(kududziGame, logger), config);
    }
}
