package org.tb.gg.collision

import groovyjarjarantlr4.v4.runtime.misc.Tuple2
import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.utils.CollectionUtils

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

    // TODO: Fix for rotated rects.
    private static boolean detectCollision(Rect a, Rect b) {
        def rectAXRange = CollisionUtils.Range.create(a.topLeft.x, a.topRight.x)
        def rectBXRange = CollisionUtils.Range.create(b.topLeft.x, b.topRight.x)
        if (!CollisionUtils.doRangesOverlap(rectAXRange, rectBXRange)) {
            return false
        }
        def rectAYRange = CollisionUtils.Range.create(a.bottomLeft.y, a.topLeft.y)
        def rectBYRange = CollisionUtils.Range.create(b.bottomLeft.y, b.topLeft.y)

        return CollisionUtils.doRangesOverlap(rectAYRange, rectBYRange)
    }

    private static boolean detectCollision(Line lineA, Line lineB) {
        def lineADirectionNorm = (lineA.start - lineA.end).normalize()
        def lineBDirectionNorm = (lineB.start - lineB.end).normalize()
        def lineADirectionNormInverted = (lineA.end - lineA.start).normalize()
        def lineBDirectionNormInverted = (lineB.end - lineB.start).normalize()
        // TODO: Is there no easier way to check this that two lines are on the same axis?
        def areOnSameLine = lineADirectionNorm == lineBDirectionNorm || lineADirectionNorm == lineBDirectionNormInverted || lineADirectionNormInverted == lineBDirectionNormInverted || lineADirectionNormInverted == lineBDirectionNormInverted
        if (areOnSameLine) {
            return lineA.isPointWithin(lineB.start) || lineA.isPointWithin(lineB.end)
        }

        def lineAPerpendicular = (lineA.end - lineA.start).rotate(MathConstants.HALF_PI)
        def onDifferentSidesOfAxisA = lineAPerpendicular.dot(lineB.start - lineA.start) * lineAPerpendicular.dot(lineB.end - lineA.start) < 0
        if (!onDifferentSidesOfAxisA) {
            return false
        }
        def lineBPerpendicular = (lineB.end - lineB.start).rotate(MathConstants.HALF_PI)
        def onDifferentSidesOfAxisB = lineBPerpendicular.dot(lineA.start - lineB.start) * lineBPerpendicular.dot(lineA.end - lineB.start) < 0
        if (!onDifferentSidesOfAxisB) {
            return false
        }

        return true
    }

    private static boolean detectCollision(Point a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Circle circle, Rect rect) {
        if (rect.rotation > 0) {
            def normalized = normalizeOrientedRectAndCircle(rect, circle)
            rect = normalized.getItem1()
            circle = normalized.getItem2()
        }
        def closestPointInRectToCircleCenter = circle.center.clampOnRange(rect.bottomLeft, rect.topRight)
        return circle.isPointWithin(closestPointInRectToCircleCenter)
    }

    private static Tuple2<Rect, Circle> normalizeOrientedRectAndCircle(Rect rect, Circle circle) {
        def rectToCircle = circle.center - rect.center
        def rectToCircleWithoutRotation = rectToCircle.rotate(-rect.rotation)
        def rectWithoutRotationAt0 = new Rect(new Vector(x: 0, y: rect.dim.y), rect.dim)

        def normalizedCircle = new Circle(center: rectWithoutRotationAt0.center + rectToCircleWithoutRotation, radius: circle.radius)
        def normalizedRect = rectWithoutRotationAt0

        return new Tuple2<Rect, Circle>(normalizedRect, normalizedCircle)
    }

    private static boolean detectCollision(Circle circle, Line line) {
        if (circle.isPointWithin(line.start) || circle.isPointWithin(line.end) || circle.isPointWithin(line.center)) {
            return true
        }
        def lineDirection = line.end - line.start
        def closestPointOnLineToCircleCenter = getClosestPointToCircleCenterOnLine(circle, line)
        def lineCenterToClosestPoint = closestPointOnLineToCircleCenter - line.center
        return circle.isPointWithin(closestPointOnLineToCircleCenter)
                && lineCenterToClosestPoint.length() < line.length / 2
                && lineDirection.isInSameDirection(lineCenterToClosestPoint)
    }

    private static Vector getClosestPointToCircleCenterOnLine(Circle circle, Line line) {
        def circleCenterToCenter = circle.center - line.center
        circleCenterToCenter.projectOnto(line.end - line.start)
    }

    private static boolean detectCollision(Circle a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Rect rect, Line line) {
        if (rect.rotation > 0) {
            def normalized = normalizeOrientedRectAndLine(rect, line)
            rect = normalized.getItem1()
            line = normalized.getItem2()
        }

        if (!checkLineRectCollision(rect, line)) {
            return false
        }
        def rectXRange = CollisionUtils.Range.create(rect.topLeft.x, rect.topRight.x)
        def lineXRange = CollisionUtils.Range.create(line.start.x, line.end.x)
        def doesXRangeOverlap = CollisionUtils.doRangesOverlap(lineXRange, rectXRange)
        if (!doesXRangeOverlap) {
            return false
        }

        def rectYRange = CollisionUtils.Range.create(rect.bottomLeft.y, rect.topLeft.y)
        def lineYRange = CollisionUtils.Range.create(line.start.y, line.end.y)
        def doesYRangeOverlap = CollisionUtils.doRangesOverlap(lineYRange, rectYRange)
        return doesYRangeOverlap
    }

    private static Tuple2<Rect, Line> normalizeOrientedRectAndLine(Rect rect, Line line) {
        def rectToLineStart = line.start - rect.center
        def rectToLineEnd = line.end - rect.center
        def rectToLineStartWithoutRotation = rectToLineStart.rotate(-rect.rotation)
        def rectToLineEndWithoutRotation = rectToLineEnd.rotate(-rect.rotation)
        def rectWithoutRotationAt0 = new Rect(new Vector(x: 0, y: rect.dim.y), rect.dim)

        def newLineStart = rectWithoutRotationAt0.center + rectToLineStartWithoutRotation
        def newLineEnd = rectWithoutRotationAt0.center + rectToLineEndWithoutRotation

        def normalizedLine = new Line(newLineStart, newLineEnd)
        def normalizedRect = rectWithoutRotationAt0

        return new Tuple2<>(normalizedRect, normalizedLine)
    }


    private static boolean checkLineRectCollision(Rect a, Line b) {
        def perpendicularToLine = (b.end - b.start).rotate(MathConstants.HALF_PI)
        def topLeft = a.topLeft - b.start
        def topRight = a.topRight - b.start
        def bottomRight = a.bottomRight - b.start
        def bottomLeft = a.bottomLeft - b.start
        def dp1 = perpendicularToLine.dot(topLeft)
        def dp2 = perpendicularToLine.dot(topRight)
        def dp3 = perpendicularToLine.dot(bottomRight)
        def dp4 = perpendicularToLine.dot(bottomLeft)
        return dp1 * dp2 <= 0 || dp2 * dp3 <= 0 || dp3 * dp4 <= 0
    }

    private static boolean detectCollision(Rect rect, Point point) {
        if (rect.rotation > 0) {
            def rectCenterToPoint = point.pos - rect.center
            def rectCenterToPointWithoutRotation = rectCenterToPoint.rotate(-rect.rotation)
            def rectWithoutRotationAt0 = new Rect(new Vector(x: 0, y: rect.dim.y), rect.dim)

            point = new Point(pos: rectWithoutRotationAt0.center + rectCenterToPointWithoutRotation)
            rect = rectWithoutRotationAt0
        }
        rect.isPointWithin(point.center)
    }

    private static Tuple2<Rect, Point> normalizeOrientedRectAndPoint(Rect rect, Point point) {
        def rectCenterToPoint = point.pos - rect.center
        def rectCenterToPointWithoutRotation = rectCenterToPoint.rotate(-rect.rotation)
        def rectWithoutRotationAt0 = new Rect(new Vector(x: 0, y: rect.dim.y), rect.dim)

        def normalizedPoint = new Point(pos: rectWithoutRotationAt0.center + rectCenterToPointWithoutRotation)
        def normalizedRect = rectWithoutRotationAt0

        return new Tuple2<Rect, Point>(normalizedRect, normalizedPoint)
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
