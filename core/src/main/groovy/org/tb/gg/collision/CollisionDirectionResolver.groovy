package org.tb.gg.collision

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.Direction
import org.tb.gg.global.geom.Vector

class CollisionDirectionResolver implements Singleton {

    Direction resolveCollisionDirections(Collision collision) {
        // TODO: Get the collision direction for each combination.
        if (collision.a.body.shape instanceof Rect && collision.b.body.shape instanceof Rect) {
            return resolveCollisionDirectionsForRectVsRect(collision)
        }
        return null
    }


    private Direction resolveCollisionDirectionsForRectVsRect(Collision collision) {
        def previousCenterA = collision.a.physicsComponent.previousCenter
        def previousCenterB = collision.b.physicsComponent.previousCenter
        def previousRectA = new Rect(previousCenterA - ((Rect) collision.a.body.shape).dim / 2.0, ((Rect) collision.a.body.shape).dim)
        def previousRectB = new Rect(previousCenterB - ((Rect) collision.b.body.shape).dim / 2.0, ((Rect) collision.b.body.shape).dim)

        def previousARangeX = CollisionUtils.Range.create(previousRectA.topLeft.x, previousRectA.topRight.x)
        def previousBRangeX = CollisionUtils.Range.create(previousRectB.topLeft.x, previousRectB.topRight.x)

        def aRangeX = CollisionUtils.Range.create(((Rect) collision.a.body.shape).topLeft.x, ((Rect) collision.a.body.shape).topRight.x)
        def bRangeX = CollisionUtils.Range.create(((Rect) collision.b.body.shape).topLeft.x, ((Rect) collision.b.body.shape).topRight.x)

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
