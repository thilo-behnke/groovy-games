package org.tb.gg.collision

import org.tb.gg.di.definition.Service
import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector

class ShapeCollisionDetector implements Singleton {

    boolean detectCollision(Shape a, Shape b) {
        if (a instanceof Circle && b instanceof Circle) {
            return detectCollision((Circle) a, (Circle) b)
        } else if (a instanceof Circle && b instanceof Rect) {
            return detectCollision((Circle) a, (Rect) b)
        } else if (a instanceof Circle && b instanceof Line) {
            return detectCollision((Circle) a, (Line) b)
        } else if (a instanceof Circle && b instanceof Point) {
            return detectCollision((Circle) a, (Point) b)
        }

        if (a instanceof Rect && b instanceof Rect) {
            return detectCollision((Rect) a, (Rect) b)
        } else if (a instanceof Rect && b instanceof Circle) {
            return detectCollision((Circle) b, (Rect) a)
        } else if (a instanceof Rect && b instanceof Line) {
            return detectCollision((Rect) a, (Line) b)
        } else if (a instanceof Rect && b instanceof Point) {
            return detectCollision((Rect) a, (Point) b)
        }

        if (a instanceof Line && b instanceof Circle) {
            return detectCollision((Circle) b, (Line) a)
        } else if (a instanceof Line && b instanceof Line) {
            return detectCollision((Line) a, (Line) b)
        } else if (a instanceof Line && b instanceof Rect) {
            return detectCollision((Rect) b, (Line) a)
        } else if (a instanceof Line && b instanceof Point) {
            return detectCollision((Line) a, (Point) b)
        }

        if (a instanceof Point && b instanceof Circle) {
            return detectCollision((Circle) b, (Point) a)
        } else if (a instanceof Point && b instanceof Line) {
            return detectCollision((Point) a, (Line) b)
        } else if (a instanceof Point && b instanceof Rect) {
            return detectCollision((Rect) b, (Point) a)
        } else if (a instanceof Point && b instanceof Point) {
            return detectCollision((Point) a, (Point) b)
        }

        throw new IllegalArgumentException("No implementation for collision check between ${a.class} and ${b.class}".toString())
    }


    boolean detectCollision(Circle a, Circle b) {
        (b.center - a.center).length() <= a.radius + b.radius
    }

    boolean detectCollision(Rect a, Rect b) {
        // TODO: Implement
        return false
    }

    boolean detectCollision(Line lineA, Line lineB) {
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

    boolean detectCollision(Point a, Point b) {
        a.isPointWithin(b.center)
    }

    boolean detectCollision(Circle circle, Rect rect) {
        def closestPointInRectToCircleCenter = circle.center.clampOnRange(rect.topLeft)
        return circle.isPointWithin(closestPointInRectToCircleCenter)
    }

    boolean detectCollision(Circle circle, Line line) {
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

    Vector getClosestPointToCircleCenterOnLine(Circle circle, Line line) {
        def circleCenterToCenter = circle.center - line.center
        circleCenterToCenter.projectOnto(line.end - line.start)
    }

    boolean detectCollision(Circle a, Point b) {
        a.isPointWithin(b.center)
    }

    boolean detectCollision(Rect a, Line b) {
        // TODO: Implement
        return false
    }

    boolean detectCollision(Rect a, Point b) {
        a.isPointWithin(b.center)
    }

    boolean detectCollision(Line a, Point b) {
        a.isPointWithin(b.center)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
