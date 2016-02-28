package com.fuzzywave.commons;

import com.fuzzywave.commons.game.GameContainer;

/**
 * Common interface for listening to {@link GameContainer} resize.
 */
public interface GameResizeListener {
    void onResize(int width, int height);
}
