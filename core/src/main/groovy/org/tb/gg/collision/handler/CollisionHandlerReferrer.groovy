package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.MultiInject

class CollisionHandlerReferrer {
    @MultiInject private List<GameObjectCollisionHandler> collisionHandlers

    void handleCollision(Collision collision) {
        def handlers = getMatchingCollisionHandlers(collision)
        handlers.each {
            it.handleCollision(collision.a, collision.b)
        }
    }

    private getMatchingCollisionHandlers(Collision collision) {
        collisionHandlers.findAll {
            it.validForTypes(collision.a, collision.b)
        }
    }
}
