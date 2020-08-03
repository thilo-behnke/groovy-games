package org.tb.gg.gameObject.shape

import org.tb.gg.collision.Collidable
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
    Vector getCenter() {
        return start + ((end - start) / 2.0)
    }

    @Override
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        def centerToPoint = (center + direction)
        def perpendicularToLine = (end - start).perpendicular()
        def perpendicularFromPoint = centerToPoint + perpendicularToLine

        if (isPointWithin(perpendicularFromPoint)) {
            return perpendicularFromPoint
        }
        // If the perpendicular does not fall onto the line, take either the start or end, given what is closer.
        def lengthPointToStart = (start - perpendicularFromPoint).length()
        def lengthPointToEnd = (end - perpendicularFromPoint).length()
        return lengthPointToStart < lengthPointToEnd ? start : end
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

    @Override
    boolean doesOverlapWith(Shape shape) {
        // TODO: Implement.
        return false
    }
}
