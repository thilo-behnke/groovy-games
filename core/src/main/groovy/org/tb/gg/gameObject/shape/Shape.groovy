package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.Renderable

interface Shape extends Renderable {
    Vector getCenter()
    Vector getClosestPointInDirectionFromCenter(Vector direction)
    boolean doesOverlapWith(Shape shape)
    boolean isPointWithin(Vector pos)
    // TODO: Implement for collision detection between shapes.
//    boolean isShapeWithin(Shape shape)
}