package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.global.Direction

class CollisionDirectionResolver implements Singleton {

    Direction resolveCollisionDirections(Collision collision) {
        return resolveCollisionDirection(collision)
    }


    private Direction resolveCollisionDirection(Collision collision) {
        def boundingRectA = collision.a.body.boundingRect
        def boundingRectB = collision.b.body.boundingRect
        def previousRectA = collision.a.physicsComponent.previousShape.boundingRect
        def previousRectB = collision.b.physicsComponent.previousShape.boundingRect

        def previousARangeX = CollisionUtils.Range.create(previousRectA.topLeft.x, previousRectA.topRight.x)
        def previousBRangeX = CollisionUtils.Range.create(previousRectB.topLeft.x, previousRectB.topRight.x)

        def aRangeX = CollisionUtils.Range.create(boundingRectA.topLeft.x, boundingRectA.topRight.x)
        def bRangeX = CollisionUtils.Range.create(boundingRectB.topLeft.x, boundingRectB.topRight.x)

        if (!CollisionUtils.doRangesOverlap(previousARangeX, previousBRangeX) && CollisionUtils.doRangesOverlap(aRangeX, bRangeX)) {
            if (collision.a.body.center.x >= collision.b.body.center.x) {
                return Direction.LEFT
            }
            return Direction.RIGHT
        } else {
            if (collision.a.body.center.y >= collision.b.body.center.y) {
                return Direction.DOWN
            }
            return Direction.UP
        }
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
