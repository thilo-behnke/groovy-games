package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.MultiInject

class CollisionHandlerReferrer {
    @MultiInject
    private List<GameObjectCollisionHandler> collisionHandlers

    void handleCollision(Collision collision) {
        collisionHandlers.each { CollisionHandler collisionHandler ->
            collisionHandler.handleCollision(collision.a, collision.b)
        }
    }
}
