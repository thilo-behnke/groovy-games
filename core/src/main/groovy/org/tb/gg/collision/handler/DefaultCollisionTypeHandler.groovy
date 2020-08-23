package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType
import org.tb.gg.global.geom.Vector

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

//            def aToB = collision.b.body.center - collision.a.body.center
//            if (collision.a.physicsComponent.velocity.dot(aToB) > 0) {
//                collision.a.physicsComponent.velocity = Vector.zeroVector()
//            }
        } else {
            // Slightly move b away from a.
            def newCenter = collision.b.body.center - collision.b.physicsComponent.velocity.normalize() * 10.0
            collision.b.body.setCenter(newCenter)

//            def bToA = collision.a.body.center - collision.b.body.center
//            if (collision.b.physicsComponent.velocity.dot(bToA) > 0) {
//                collision.b.physicsComponent.velocity = Vector.zeroVector()
//            }
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
