package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.Direction

class CollisionDirectionResolver implements Singleton {

    EnumSet<Direction> resolveCollisionDirections(Collision collision) {
        // TODO: Get the collision direction for each combination.
        if (collision.a.body.shape instanceof Rect && collision.b.body.shape instanceof Rect) {
            return resolveCollisionDirectionsForShape((Rect) collision.a.body.shape, (Rect) collision.b.body.shape)
        }
    }


    private EnumSet<Direction> resolveCollisionDirectionsForShape(Rect rect1, Rect rect2) {
//        if (rect1.bottomLeft.y >= rect2.topLeft.y && rect1.bottomLeft.y < rect2.center.y) {
//            return [Direction.UP].toSet()
//        }

    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
