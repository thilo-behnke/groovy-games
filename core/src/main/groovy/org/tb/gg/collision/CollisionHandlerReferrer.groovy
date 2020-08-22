package org.tb.gg.collision

import org.tb.gg.di.Inject
import org.tb.gg.di.MultiInject
import org.tb.gg.gameObject.GameObject

class CollisionHandlerReferrer {
    @MultiInject private List<CollisionHandler> collisionHandlers

    void handleCollision(Collision collision) {

    }

    private getMatchingCollisionHandlers(Collision collision) {
        GameObject a = collision.a
        GameObject b = collision.b

    }
}
