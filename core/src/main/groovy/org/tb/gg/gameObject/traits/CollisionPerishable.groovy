package org.tb.gg.gameObject.traits

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.Perishable

trait CollisionPerishable implements Perishable, GameObject {

    @Inject CollisionRegistry collisionRegistry

    @SuppressWarnings('unused')
    Boolean shouldPerish__CollisionPerishable() {
        // TODO: Inefficient, is iterated over twice.
        def collidingGameObjects = getCollidingGameObjects()
        collidingGameObjects.any { GameObject collidingGameObject ->
           physicsComponent.collidesWithGroups.contains(collidingGameObject.physicsComponent.collisionGroup)
        }
    }

    Set<GameObject> getCollidingGameObjects() {
        if (!getPhysicsComponent().collides) {
            return null
        }
        collisionRegistry.getCollisions(this).collect { Collision collision ->
            def a = collision.a
            def b = collision.b
            a == this ? b : a
        }
    }
}