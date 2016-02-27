package com.fuzzywave.commons.util;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class ScreenConfig {

    public enum ScreenSize {
        SMALL(240, 320), NORMAL(800, 480), LARGE(1280, 720), XLARGE(1920, 1080);

        int width;
        int height;

        ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public enum ScreenDensity {
        LLDPI(90), LDPI(120), MDPI(160), HDPI(240), XHDPI(320), XXHDPI(480), XXXHDPI(640);

        int value;

        ScreenDensity(int value) {
            this.value = value;
        }
    }

    public static void setScreenSize(ScreenSize screenSize,
                                     ScreenDensity screenDensity,
                                     LwjglApplicationConfiguration config) {
        config.width = screenSize.width;
        config.height = screenSize.height;
        config.overrideDensity = screenDensity.value;
    }
}
