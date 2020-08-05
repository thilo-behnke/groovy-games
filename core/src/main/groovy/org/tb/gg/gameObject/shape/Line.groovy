package org.tb.gg.gameObject.shape

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

import java.awt.Rectangle

class Line implements Shape {
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
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        if (direction.length() < (end - center).length()) {
            def directionOnLine = BigDecimal.valueOf(center.dot(direction).signum())
            def normalizedVector = (end - start).normalize()
            return center + normalizedVector * directionOnLine * direction.length()
        }

        def distanceToStart = (start - direction).length()
        def distanceToEnd = (end - direction).length()
        if(distanceToStart == distanceToEnd) {
            return center
        }
        return distanceToStart < distanceToEnd ? start : end
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
        if (shape.isPointWithin(start) || shape.isPointWithin(center) || shape.isPointWithin(end)) {
            return true
        }
        if (shape instanceof Line) {
            def line = (Line) shape
            return lineOverlapGoldmanAlgorithm(line)
        }
        // TODO: Is there no better way to handle this without differentiating all shapes?
        return shape.doesOverlapWith(this)
    }

    private boolean lineOverlapGoldmanAlgorithm(Line line) {
        // https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
        // p + t r = q + u s
        def endCrossProduct = end.cross(line.end)
        if (endCrossProduct == 0) {
            return false
        }
        // u = (q − p) × r / (r × s)
        def u = (line.start - start).cross(end) / endCrossProduct
        if (u < 0 || u > 1) {
            return false
        }
        // t = (q − p) × s / (r × s)
        def t = (line.start - start).cross(line.end) / endCrossProduct
        if (t < 0 || t > 1) {
            return false
        }
        return true
    }
}
