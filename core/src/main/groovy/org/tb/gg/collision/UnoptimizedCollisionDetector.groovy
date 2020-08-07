package org.tb.gg.collision


import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import org.tb.gg.utils.CollectionUtils

class UnoptimizedCollisionDetector implements CollisionDetector {
    @Override
    Set<Collision> detect(Set<GameObject> gameObjects) {
        if (gameObjects.size() < 2) {
            return []
        }
        // TODO: Permutations for each object with each object (beware of duplicates).
        def combinations = CollectionUtils.permutations(gameObjects, 2)

        combinations
                .collect { GameObject a, GameObject b ->
                    if (a.physicsComponent?.collidesWith(b.physicsComponent?.getStructure())) {
                        return new Collision(a: a, b: b)
                    }
                    return null
                }
                .findAll { it }
                .collect { (Collision) it }
    }

    private boolean detectCollision(Shape a, Shape b) {
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
        } else if (a instanceof Rect && b instanceof Line) {
            return detectCollision((Rect) a, (Line) b)
        } else if (a instanceof Rect && b instanceof Point) {
            return detectCollision((Rect) a, (Point) b)
        }

        if (a instanceof Line && b instanceof Line) {
            return detectCollision((Line) a, (Line) b)
        } else if (a instanceof Line && b instanceof Point) {
            return detectCollision((Line) a, (Point) b)
        }

        if (a instanceof Point && b instanceof Point) {
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

    private static boolean detectCollision(Point a, Point b) {
        a.isPointWithin(b.center)
    }

    private static boolean detectCollision(Circle circle, Rect rect) {
        def closestPointInRectToCircleCenter = circle.center.clampOnRange(rect.topLeft)
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
