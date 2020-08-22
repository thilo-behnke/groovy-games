package org.tb.gg.collision


import org.tb.gg.di.MultiInject

class CollisionHandlerReferrer {
    @MultiInject private List<CollisionHandler> collisionHandlers

    void handleCollision(Collision collision) {
        def handlers = getMatchingCollisionHandlers(collision)
        handlers.each {
            it.handleCollision(collision.a, collision.b)
        }
    }

    private getMatchingCollisionHandlers(Collision collision) {
        collisionHandlers.findAll { it.validForTypes(collision.a, collision.b) }
    }
}
