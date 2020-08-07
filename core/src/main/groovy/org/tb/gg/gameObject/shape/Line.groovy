package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Line extends Shape {
    Vector start
    // TODO: Model with length instead of end.
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
    Vector getCenter() {
        return start + ((end - start) / 2.0)
    }

    @Override
    void setCenter(Vector vec) {
        def newStart = vec + (start - center)
        def newEnd = vec + (end - center)
        start = newStart
        end = newEnd
    }

    @Override
    boolean isPointWithin(Vector pos) {
        if (pos == Vector.zeroVector() && (start == Vector.zeroVector() || end == Vector.zeroVector())) {
            return true
        }
        def onSameLine = (end - start).normalize() == pos.normalize()
        if (!onSameLine) {
            return false
        }
        def betweenStartAndEnd = start.x <= pos.x && start.y <= pos.y && pos.x <= end.x && pos.y <= end.y
        return betweenStartAndEnd
    }

    BigDecimal getLength() {
       return (end - start).length()
    }
}
