package org.tb.gg.gameObject.shape

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
    boolean isPointWithin(Vector pos) {
        return (pos - center).length() <= radius
    }
}
