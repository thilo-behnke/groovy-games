package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector

class ShapeCollisions {

    static boolean detectCollision(Circle a, Circle b) {
        (b.center - a.center).length() <= a.radius + b.radius
    }

    static boolean detectCollision(Rect a, Rect b) {
        // TODO: Implement
        return false
    }

    static boolean detectCollision(Line lineA, Line lineB) {
        // https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
        // p + t r = q + u s
        def endCrossProduct = lineB.end.cross(lineA.end)
        if (endCrossProduct == 0) {
            return false
        }
        // u = (q − p) × r / (r × s)
        def u = (lineA.start - lineB.start).cross(lineB.end) / endCrossProduct
        if (u < 0 || u > 1) {
            return false
        }
        // t = (q − p) × s / (r × s)
        def t = (lineA.start - lineB.start).cross(lineA.end) / endCrossProduct
        if (t < 0 || t > 1) {
            return false
        }
        return true
    }

    static boolean detectCollision(Point a, Point b) {
        a.isPointWithin(b.center)
    }

    static boolean detectCollision(Circle circle, Rect rect) {
        def closestPointInRectToCircleCenter = circle.center.clampOnRange(rect.topLeft)
        return circle.isPointWithin(closestPointInRectToCircleCenter)
    }

    static boolean detectCollision(Circle circle, Line line) {
        if (circle.isPointWithin(line.start) || circle.isPointWithin(line.end) || circle.isPointWithin(line.center)) {
            return true
        }
        def lineDirection = line.end - line.start
        def closestPointOnLineToCircleCenter = getClosestPointToCircleCenterOnLine(circle, line)
        def lineCenterToClosestPoint = closestPointOnLineToCircleCenter - line.center
        return circle.isPointWithin(closestPointOnLineToCircleCenter)
                && lineCenterToClosestPoint < line.length / 2
                && lineDirection.isInSameDirection(lineCenterToClosestPoint)
    }

    static Vector getClosestPointToCircleCenterOnLine(Circle circle, Line line) {
        def circleCenterToCenter = circle.center - line.center
        circleCenterToCenter.projectOnto(line.end - line.start)
    }

    static boolean detectCollision(Circle a, Point b) {
        a.isPointWithin(b.center)
    }

    static boolean detectCollision(Rect a, Line b) {
        // TODO: Implement
        return false
    }

    static boolean detectCollision(Rect a, Point b) {
        a.isPointWithin(b.center)
    }

    static boolean detectCollision(Line a, Point b) {
        a.isPointWithin(b.center)
    }

}
