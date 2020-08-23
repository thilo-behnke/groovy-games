package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType

class DefaultCollisionTypeHandler implements CollisionTypeHandler {
    @Override
    void handleCollisionByType(Collision collision) {
        if (collision.type == CollisionType.OVERLAP) {
            return
        }
        // TODO: Handle solid.
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
