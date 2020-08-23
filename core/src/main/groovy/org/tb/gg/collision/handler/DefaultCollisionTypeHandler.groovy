package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType

class DefaultCollisionTypeHandler implements CollisionTypeHandler {
    @Override
    void handleCollisionByType(Collision collision) {
        if (collision.type == CollisionType.OVERLAP) {
            return
        }
        def objectWithHigherVelocity = collision.a.physicsComponent.velocity.abs().sum() >= collision.b.physicsComponent.velocity.abs().sum() ? collision.a : collision.b
        if (objectWithHigherVelocity == collision.a) {
            // Slightly move a away from b.
            def newCenter = collision.a.body.center - collision.a.physicsComponent.velocity.normalize() * 10.0
            collision.a.body.setCenter(newCenter)
        } else {
            // Slightly move b away from a.
            def newCenter = collision.b.body.center - collision.b.physicsComponent.velocity.normalize() * 10.0
            collision.b.body.setCenter(newCenter)
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
