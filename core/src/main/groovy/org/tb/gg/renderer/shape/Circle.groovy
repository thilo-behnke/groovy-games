package org.tb.gg.renderer.shape

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Circle implements Shape {
    Vector center
    BigDecimal radius

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawCircle(center, radius, options)
    }

    @Override
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        center + direction.normalize() * radius
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return (pos - center).length() <= radius
    }

    @Override
    boolean doesOverlapWith(Collidable collidable) {
        def shapeToCircle = center - collidable.center
        def closestPointOnShape = collidable.getClosestPointInDirectionFromCenter(shapeToCircle)
        def centerToClosestPointOnShape = closestPointOnShape - center
        centerToClosestPointOnShape.length() < radius
    }
}
