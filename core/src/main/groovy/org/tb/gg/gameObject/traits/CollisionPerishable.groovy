package org.tb.gg.gameObject.traits


import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject

trait CollisionPerishable implements Perishable, GameObject {

    @Inject CollisionRegistry collisionRegistry

    // TODO: Combine multiple perishable traits by ast transformation.
    @Override
    Boolean shouldPerish() {
        // TODO: Inefficient, is iterated over twice.
        def collidingGameObjects = getCollidingGameObjects()
        collidingGameObjects.any { GameObject collidingGameObject ->
           physicsComponent.collidesWithGroups.contains(collidingGameObject.physicsComponent.collisionGroup)
        }
    }

    private Set<GameObject> getCollidingGameObjects() {
        if (!getPhysicsComponent().collides) {
            return null
        }
        collisionRegistry.getCollisions(this).collect { GameObject a, GameObject b ->
            a == this ? b : a
        }
    }
}