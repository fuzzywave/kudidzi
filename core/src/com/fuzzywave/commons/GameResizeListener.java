package com.fuzzywave.commons;

/**
 * Common interface for listening to {@link GameContainer} resize.
 */
public interface GameResizeListener {
    void onResize(int width, int height);
}
