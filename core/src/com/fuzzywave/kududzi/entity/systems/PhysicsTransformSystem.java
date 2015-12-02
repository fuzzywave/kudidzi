package com.fuzzywave.kududzi.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.fuzzywave.kududzi.entity.components.ComponentRetriever;
import com.fuzzywave.kududzi.entity.components.PhysicsComponent;
import com.fuzzywave.kududzi.entity.components.TransformComponent;

/**
 * Transforms between previous and current physics states of the entities.
 * Also, this system can be used to interpolate between physics states based on
 * the alpha value (how much time is left in the accumulator).
 */
public class PhysicsTransformSystem extends IteratingSystem {

    private float alpha;

    public PhysicsTransformSystem(Family family, int priority) {
        super(Family.all(PhysicsComponent.class,
                TransformComponent.class).get(), priority);
        alpha = 1.0f;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physics = ComponentRetriever.get(entity, PhysicsComponent.class);
        TransformComponent transform = ComponentRetriever.get(entity, TransformComponent.class);

        // TODO override component

        transform.position.x = physics.position.x * alpha + transform.position.x * (1.0f - alpha);
        transform.position.y = physics.position.y * alpha + transform.position.y * (1.0f - alpha);
        transform.angle = physics.angle * alpha + transform.angle * (1.0f - alpha);
    }

    public void interpolate(float alpha) {
        this.alpha = alpha;
        super.update(1.0f);
        this.alpha = 1.0f;
    }
}
