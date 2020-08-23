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
            def aToB = collision.a.getBody().center - collision.b.getBody().center
            // Slightly move a away from b.
            collision.a.body.center = collision.a.body.center - collision.a.physicsComponent.velocity.normalize() * 10.0
        } else {
            def bToA = collision.b.getBody().center - collision.a.getBody().center
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
