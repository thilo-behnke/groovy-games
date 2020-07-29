package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Text implements Shape {
    Vector pos
    String text

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawText(pos, text, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        // TODO: Given this definition, is text really a shape?
        return false
    }
}
