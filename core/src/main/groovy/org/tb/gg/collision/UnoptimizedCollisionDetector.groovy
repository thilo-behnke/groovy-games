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
        def combinations = CollectionUtils.permutations(gameObjects, 2)

        combinations
                .collect { GameObject a, GameObject b ->
                    if(!a.physicsComponent || !b.physicsComponent) {
                        return null
                    }
                    return detectCollision(a.physicsComponent.getStructure(), b.physicsComponent.getStructure())
                }
                .findAll { it }
                .collect { (Collision) it }
    }

    static boolean detectCollision(Shape a, Shape b) {
        if (a instanceof Circle && b instanceof Circle) {
            return ShapeCollisions.detectCollision((Circle) a, (Circle) b)
        } else if (a instanceof Circle && b instanceof Rect) {
            return ShapeCollisions.detectCollision((Circle) a, (Rect) b)
        } else if (a instanceof Circle && b instanceof Line) {
            return ShapeCollisions.detectCollision((Circle) a, (Line) b)
        } else if (a instanceof Circle && b instanceof Point) {
            return ShapeCollisions.detectCollision((Circle) a, (Point) b)
        }

        if (a instanceof Rect && b instanceof Rect) {
            return ShapeCollisions.detectCollision((Rect) a, (Rect) b)
        } else if (a instanceof Rect && b instanceof Line) {
            return ShapeCollisions.detectCollision((Rect) a, (Line) b)
        } else if (a instanceof Rect && b instanceof Point) {
            return ShapeCollisions.detectCollision((Rect) a, (Point) b)
        }

        if (a instanceof Line && b instanceof Line) {
            return ShapeCollisions.detectCollision((Line) a, (Line) b)
        } else if (a instanceof Line && b instanceof Point) {
            return ShapeCollisions.detectCollision((Line) a, (Point) b)
        }

        if (a instanceof Point && b instanceof Point) {
            return ShapeCollisions.detectCollision((Point) a, (Point) b)
        }

        throw new IllegalArgumentException("No implementation for collision check between ${a.class} and ${b.class}".toString())
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
