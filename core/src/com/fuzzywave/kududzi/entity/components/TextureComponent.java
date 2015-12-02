package com.fuzzywave.kududzi.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;


public class TextureComponent implements Pool.Poolable, Component {
    public TextureRegion region;

    public TextureComponent() {
        region = null;
    }

    public TextureComponent(TextureComponent other) {
        region = other.region;
    }

    @Override
    public void reset() {
        region = null;
    }
}