package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Rect implements Shape {
    private Vector topLeft
    private Vector dim

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

    // TODO: Add to shape interface + cache.
    private getCenter() {
        topLeft + dim * new Vector(x: 1, y: -1) / 2.0
    }
}
