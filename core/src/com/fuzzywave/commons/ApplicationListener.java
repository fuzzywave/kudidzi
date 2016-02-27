package com.fuzzywave.commons;

import com.badlogic.gdx.Application;


/**
 * Extends {@link com.badlogic.gdx.ApplicationListener} to add update and interpolate methods.
 */
public interface ApplicationListener extends com.badlogic.gdx.ApplicationListener {
    /**
     * Called when the {@link Application} should update itself.
     */
    public void update(float delta);

    /**
     * Called when the {@link Application} should interpolate itself.
     */
    public void interpolate(float alpha);
}
