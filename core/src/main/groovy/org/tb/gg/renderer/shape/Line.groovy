package org.tb.gg.renderer.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Line implements Shape {
    Vector start
    Vector end

    Line(Vector start, Vector end) {
        this.start = start
        this.end = end
    }

    Line scaleFromEnd(BigDecimal scaleFactor) {
        start = end + (start - end) * scaleFactor
        return this
    }

    Line scaleFromStart(BigDecimal scaleFactor) {
        end = start + (end - start) * scaleFactor
        return this
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawLine(start, end, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        def onSameLine = (end - start).normalize() == pos.normalize()
        if(!onSameLine) {
            return false
        }
        def betweenStartAndEnd = start.x <= pos.x && start.y <= pos.y && pos.x <= end.x && pos.y <= end.y
        return betweenStartAndEnd
    }
}
