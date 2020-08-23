package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType

class DefaultCollisionTypeHandler implements CollisionTypeHandler {
    @Override
    void handleCollisionByType(Collision collision) {
        if (collision.type == CollisionType.OVERLAP) {
            return
        }
        def objectWithHigherVelocity = collision.a.physicsComponent.physicStats.velocity.abs().sum() >= collision.b.physicsComponent.physicStats.velocity.abs().sum() ? collision.a : collision.b
        if (objectWithHigherVelocity == collision.a) {
            // Slightly move a away from b.
            collision.a.body.center = collision.a.body.center - collision.a.physicsComponent.velocity.normalize() * 10.0
        } else {
            // Slightly move b away from a.
            collision.b.body.center = collision.b.body.center - collision.b.physicsComponent.velocity.normalize() * 10.0
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
