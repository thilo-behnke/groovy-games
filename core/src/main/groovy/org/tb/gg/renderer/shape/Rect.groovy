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
}
