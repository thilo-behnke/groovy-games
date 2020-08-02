package org.tb.gg.renderer.shape

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Rect implements Shape {
    Vector topLeft
    Vector dim

    Rect(Vector topLeft, Vector dim) {
        this.topLeft = topLeft
        this.dim = dim
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawRect(topLeft, dim, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        def centerToPoint = pos - getCenter()
        centerToPoint.x.abs() <= dim.x / 2 && centerToPoint.y.abs() <= dim.y / 2
    }

    @Override
    boolean doesOverlapWith(Collidable collidable) {
        return false
    }

    @Override
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        // TODO: Can it be so tough?
        return null
    }

    // TODO: Add cache.
    @Override
    Vector getCenter() {
        topLeft + dim * Vector.invertYVector() / 2.0
    }
}
