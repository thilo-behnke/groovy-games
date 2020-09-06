package org.tb.gg.gameObject.shape

import org.tb.gg.collision.ShapeCollisionDetector
import org.tb.gg.di.Inject
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Circle extends Shape {
    Vector center
    BigDecimal radius

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawCircle(center, radius, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return (pos - center).length() <= radius
    }

    @Override
    Rect getBoundingRect() {
        return new Rect(center + new Vector(x: -radius, y: radius), Vector.unitVector() * 2.0 * radius)
    }

    @Override
    Shape copy() {
        return new Circle(center: center.copy(), radius: radius)
    }

    @Override
    void rotate(BigDecimal radians) {
        // Left intentionally empty, there is no point in rotating a circle.
    }

    @Override
    void setRotation(BigDecimal rotation) {
        // Left intentionally empty, there is no point in rotating a circle.
    }
}
