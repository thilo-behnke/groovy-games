package org.tb.gg.collision


import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector

class ShapeCollisionDetector implements Singleton {

    boolean detect(Shape a, Shape b) {
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
            return detect((Point) a, (Line) b)
        } else if (a instanceof Point && b instanceof Rect) {
            return detectCollision((Rect) b, (Point) a)
        } else if (a instanceof Point && b instanceof Point) {
            return detectCollision((Point) a, (Point) b)
        }

        throw new IllegalArgumentException("No implementation for collision check between ${a.class} and ${b.class}".toString())
    }

    private static boolean detectCollision(Circle a, Circle b) {
        (b.center - a.center).length() <= a.radius + b.radius
    }

    private static boolean detectCollision(Rect a, Rect b) {
        // TODO: Implement
        return false
    }

    private static boolean detectCollision(Line lineA, Line lineB) {
        def lineAStartToLineBStart = (lineB.start - lineA.start).normalize()
        def lineAStartToLineBEnd = (lineB.end - lineA.start).normalize()
        def lineAStartToLineAEnd = (lineA.end - lineA.start).normalize()
        def lineBStartToLineBEnd = (lineB.end - lineA.start).normalize()

        def crossFromAStartToBStartAndBEnd = lineAStartToLineBStart.cross(lineAStartToLineBEnd).abs()
        def crossFromAStartToBStartAndAEnd = lineAStartToLineBStart.cross(lineAStartToLineAEnd).abs()

        if (crossFromAStartToBStartAndBEnd < crossFromAStartToBStartAndAEnd) {
            return false
        }

        def areOnSameLine = lineAStartToLineAEnd.normalize().abs() == lineBStartToLineBEnd.normalize().abs()
        if (areOnSameLine) {
            return lineA.isPointWithin(lineB.start) || lineA.isPointWithin(lineB.end)
        }

        return true
    }

    private static boolean detectCollision(Point a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Circle circle, Rect rect) {
        def closestPointInRectToCircleCenter = circle.center.clampOnRange(rect.bottomLeft, rect.topRight)
        return circle.isPointWithin(closestPointInRectToCircleCenter)
    }

    private static boolean detectCollision(Circle circle, Line line) {
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

    private static Vector getClosestPointToCircleCenterOnLine(Circle circle, Line line) {
        def circleCenterToCenter = circle.center - line.center
        circleCenterToCenter.projectOnto(line.end - line.start)
    }

    private static boolean detectCollision(Circle a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Rect a, Line b) {
        // TODO: Implement
        return false
    }

    private static boolean detectCollision(Rect a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Line a, Point b) {
        a.isPointWithin(b.center)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
