package org.tb.gg.collision

import org.tb.gg.global.geom.Vector

interface Collidable {
    Vector getCenter()
    Vector getClosestPointInDirectionFromCenter(Vector direction)
    abstract boolean isPointWithin(Vector pos)
    // TODO: Implement for collision detection between shapes.
//    boolean isShapeWithin(Shape shape)
    boolean doesOverlapWith(Collidable collidable)
}