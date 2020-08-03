package org.tb.gg.gameObject.components.physics

import org.tb.gg.collision.NoCollisionDetection

class ShapePhysicsWithoutCollisionComponent extends ShapePhysicsComponent implements NoCollisionDetection {
    ShapePhysicsWithoutCollisionComponent(ShapeBody body) {
        super(body)
    }
}
