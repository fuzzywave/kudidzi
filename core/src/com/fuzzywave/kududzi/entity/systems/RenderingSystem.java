package com.fuzzywave.kududzi.entity.systems;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.fuzzywave.commons.graphics.Graphics;
import com.fuzzywave.kududzi.entity.components.ComponentRetriever;
import com.fuzzywave.kududzi.entity.components.TextureComponent;
import com.fuzzywave.kududzi.entity.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    private Graphics g;
    private Array<Entity> renderQueue;

    public RenderingSystem(Graphics g, int priority) {
        super(Family.all(TextureComponent.class, TransformComponent.class).get(),
                new ZComparator(), priority);

        this.renderQueue = new Array<Entity>();
        this.g = g;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        g.spriteBatch.begin();
        for (Entity entity : renderQueue) {
            // TODO render entity.
            TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
            TextureComponent textureComponent = ComponentRetriever.get(entity, TextureComponent.class);

            float angle = transformComponent.angle;
            Vector3 position = transformComponent.position;
            Vector2 size = transformComponent.size;
            float scale = transformComponent.scale;

            float width = size.x;
            float height = size.y;
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            g.spriteBatch.draw(textureComponent.region,
                    position.x - originX,
                    position.y - originY,
                    originX,
                    originY,
                    width,
                    height,
                    scale,
                    scale,
                    MathUtils.radiansToDegrees * angle);
        }
        g.spriteBatch.begin();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int) Math.signum(ComponentRetriever.get(e1, TransformComponent.class).position.z -
                    ComponentRetriever.get(e2, TransformComponent.class).position.z);
        }
    }
}
