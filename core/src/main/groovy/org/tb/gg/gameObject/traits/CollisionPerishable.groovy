package org.tb.gg.gameObject.traits

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject

trait CollisionPerishable implements Perishable, GameObject {

    @Inject CollisionRegistry collisionRegistry

    // TODO: Combine multiple perishable traits by ast transformation.
    @Override
    Boolean shouldPerish() {
        def collisions = getCollision()
        // TODO: Implement.
        return false
    }

    // TODO: Generate with AST transformation.
    private Set<String> getRelevantCollisionGroups() {
        physicsComponent.getCollisionGroups()
    }

    private Set<Collision> getCollision() {
        if (!getPhysicsComponent().collides) {
            return null
        }
        return collisionRegistry.getCollisions(this)
    }
}