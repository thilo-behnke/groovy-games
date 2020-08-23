package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.MultiInject
import org.tb.gg.di.definition.Singleton

class CollisionHandlerReferrer implements Singleton {
    @MultiInject
    private List<GameObjectCollisionHandler> collisionHandlers

    void handleCollision(Collision collision) {
        collisionHandlers.each { CollisionHandler collisionHandler ->
            collisionHandler.handleCollision(collision.a, collision.b)
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
