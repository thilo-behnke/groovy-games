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

    void setStart(Vector start) {
        if (start.x > end.x && start.y > end.y) {
            this.start = end
        } else {
            this.start = start
        }
    }

    void setEnd(Vector end) {
        if (start.x > end.x && start.y > end.y) {
            this.end = start
        } else {
            this.end = end
        }
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
        if (pos == start || pos == end) {
            return true
        }
        def lineDirection = end - start
        // Point needs to be on same infinite line as line segment.
        if ((pos - start).normalize() != lineDirection.normalize()) {
            return false
        }
        // Otherwise check the projection of the point onto the line.
        def pointProjected = pos.projectOnto(lineDirection)
        def startToPointProjected = pointProjected - start
        return startToPointProjected.length() <= lineDirection.length()
                && lineDirection.dot(startToPointProjected) >= 0
    }

    BigDecimal getLength() {
        return (end - start).length()
    }
}
