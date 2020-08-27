package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.global.Direction

class CollisionDirectionResolver implements Singleton {

    Direction resolveCollisionDirection(Collision collision) {
        // It is known here that the objects must collide, therefore it is not necessary to check if there is a collision.
        // Therefore it is enough to just check if the x ranges overlap, otherwise it is an y axis collision.
        if (isHorizontalCollision(collision)) {
            getDirectionForHorizontalCollision(collision)
        } else {
            getDirectionForVerticalCollision(collision)
        }
    }

    private static boolean isHorizontalCollision(Collision collision) {
        def boundingRectA = collision.a.body.boundingRect
        def boundingRectB = collision.b.body.boundingRect
        def previousRectA = collision.a.physicsComponent.previousShape.boundingRect
        def previousRectB = collision.b.physicsComponent.previousShape.boundingRect

        def previousARangeX = CollisionUtils.Range.create(previousRectA.topLeft.x, previousRectA.topRight.x)
        def previousBRangeX = CollisionUtils.Range.create(previousRectB.topLeft.x, previousRectB.topRight.x)

        def aRangeX = CollisionUtils.Range.create(boundingRectA.topLeft.x, boundingRectA.topRight.x)
        def bRangeX = CollisionUtils.Range.create(boundingRectB.topLeft.x, boundingRectB.topRight.x)

        !CollisionUtils.doRangesOverlap(previousARangeX, previousBRangeX) && CollisionUtils.doRangesOverlap(aRangeX, bRangeX)
    }

    private static Direction getDirectionForHorizontalCollision(Collision collision) {
        if (collision.a.body.center.x >= collision.b.body.center.x) {
            return Direction.LEFT
        }
        return Direction.RIGHT
    }

    private static Direction getDirectionForVerticalCollision(Collision collision) {
        if (collision.a.body.center.y >= collision.b.body.center.y) {
            return Direction.DOWN
        }
        return Direction.UP
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
