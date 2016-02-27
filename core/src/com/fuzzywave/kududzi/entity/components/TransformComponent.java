package com.fuzzywave.kududzi.entity.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Pool.Poolable, Component {
    public Vector3 position;
    public Vector2 size;
    public float scale;
    public float angle;

    public TransformComponent() {
        position = new Vector3();
        size = new Vector2();
        reset();
    }

    public TransformComponent(TransformComponent other) {
        position = new Vector3(other.position);
        size = new Vector2(other.size);
        scale = other.scale;
        angle = other.angle;
    }

    @Override
    public void reset() {
        position.set(0.0f, 0.0f, 0.0f);
        size.set(0.0f, 0.0f);
        scale = 1.0f;
        angle = 0.0f;
    }
}