package com.fuzzywave.kududzi.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;
import com.fuzzywave.commons.platform.LoggerImpl;
import com.fuzzywave.kududzi.KududziGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();


        LoggerImpl logger = new LoggerImpl("KududziDesktop", Logger.DEBUG);

        KududziGame.setPlatformResolvers(logger);

        new LwjglApplication(new KududziGame(), config);
    }
}
