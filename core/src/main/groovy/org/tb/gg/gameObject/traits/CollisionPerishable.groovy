package org.tb.gg.gameObject.traits

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.PerishCondition
import org.tb.gg.gameObject.Perishable

// TODO: Add ast transformation annotation to add parameters: Nr. of collisions, include / exclude collision groups.
@PerishCondition
trait CollisionPerishable implements Perishable, GameObject {

    @Inject
    CollisionRegistry collisionRegistry

    @SuppressWarnings('unused')
    Boolean shouldPerish__CollisionPerishable() {
        def collidingGameObject = getCollidingGameObject()
        return collidingGameObject != null
    }

    GameObject getCollidingGameObject() {
        if (!getPhysicsComponent().collides) {
            return null
        }
        collisionRegistry.getCollisions(this).collect { Collision collision ->
            def a = collision.a
            def b = collision.b
            a == this ? b : a
        }
        .find { GameObject collidingGameObject ->
            physicsComponent.collidesWithGroups.contains(collidingGameObject.physicsComponent.collisionGroup)
        }
    }
}