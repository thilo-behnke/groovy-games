package org.tb.gg.collision

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.framecache.FrameCache
import org.tb.gg.global.Direction

class CollisionDirectionResolver implements Singleton {

    enum CollisionDirection {
        HORIZONTAL,
        VERTICAL,
        // Used if it is not easily possible to determine the collision direction, e.g. when both objects are spawned colliding on frame 0.
        UNDEFINED
    }

    @Inject FrameCache frameCache

    Direction resolveCollisionDirection(Collision collision) {
        def collisionDirection = getCollisionDirection(collision)

        switch(collisionDirection) {
            case CollisionDirection.HORIZONTAL:
                return getDirectionForHorizontalCollision(collision)
            case CollisionDirection.VERTICAL:
                return getDirectionForVerticalCollision(collision)
            default:
                return Direction.UNDEFINED
        }
    }

    private CollisionDirection getCollisionDirection(Collision collision) {
        def boundingRectA = collision.a.body.boundingRect
        def boundingRectB = collision.b.body.boundingRect
        def previousRectA = frameCache.getLastFrames(1)[0]?.getShape(collision.a.id)?.orElse(null)?.boundingRect
        def previousRectB = frameCache.getLastFrames(1)[0]?.getShape(collision.b.id)?.orElse(null)?.boundingRect

        if (previousRectA == null || previousRectB == null) {
            return CollisionDirection.UNDEFINED
        }

        def previousARangeX = CollisionUtils.Range.create(previousRectA.topLeft.x, previousRectA.topRight.x)
        def previousBRangeX = CollisionUtils.Range.create(previousRectB.topLeft.x, previousRectB.topRight.x)
        def aRangeX = CollisionUtils.Range.create(boundingRectA.topLeft.x, boundingRectA.topRight.x)
        def bRangeX = CollisionUtils.Range.create(boundingRectB.topLeft.x, boundingRectB.topRight.x)

        if(!CollisionUtils.doRangesOverlap(previousARangeX, previousBRangeX) && CollisionUtils.doRangesOverlap(aRangeX, bRangeX)) {
            return CollisionDirection.HORIZONTAL
        }

        def previousARangeY = CollisionUtils.Range.create(previousRectA.bottomLeft.y, previousRectA.topRight.y)
        def previousBRangeY = CollisionUtils.Range.create(previousRectB.bottomLeft.y, previousRectB.topRight.y)
        def aRangeY = CollisionUtils.Range.create(boundingRectA.bottomLeft.y, boundingRectA.topRight.y)
        def bRangeY = CollisionUtils.Range.create(boundingRectB.bottomLeft.y, boundingRectB.topRight.y)

        if(!CollisionUtils.doRangesOverlap(previousARangeY, previousBRangeY) && CollisionUtils.doRangesOverlap(aRangeY, bRangeY)) {
            return CollisionDirection.VERTICAL
        }

        return CollisionDirection.UNDEFINED
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
